package com.github.xwmtp.bingotournament.match

import com.github.xwmtp.api.model.EntrantState
import com.github.xwmtp.api.model.MatchState
import com.github.xwmtp.bingotournament.match.racetime_client.RacetimeEntrant.RacetimeEntrantStatus.*
import com.github.xwmtp.bingotournament.match.racetime_client.RacetimeHttpClient
import com.github.xwmtp.bingotournament.match.racetime_client.RacetimeRace
import org.springframework.stereotype.Component

@Component
class RacetimeRecorder(private val client: RacetimeHttpClient) {

	@Throws(RecordingException::class)
	fun recordRace(racetimeId: String): DbMatch.() -> Unit {

		val race = client.getRace(racetimeId) ?: throw RacetimeHttpErrorException

		return {

			validate(this, race)

			val raceEntrants = parseEntrants(this, race)

			scheduledTime = race.startedAt
			state = MatchState.FINISHED
			this.racetimeId = racetimeId
			entrants
					.distinctBy { it.userId } // Why is this necessary?
					.onEach { raceEntrants[it.userId]?.invoke(it) }
		}
	}

	private fun validate(match: DbMatch, race: RacetimeRace) =
			when {
				!race.entrants.map { it.user.id }.containsAll(match.entrants.map { it.userId }) -> throw RacetimeInconsistencyException("Entrants missing")
				race.teamRace -> throw RacetimeInconsistencyException("Team race")
				race.status != RacetimeRace.RacetimeRaceStatus.FINISHED -> throw RacetimeInconsistencyException("Not finished")
				!race.recorded -> throw RacetimeInconsistencyException("Not recorded")
				else -> Unit
			}

	private fun parseEntrants(match: DbMatch, race: RacetimeRace): Map<String, DbEntrant.() -> Unit> {

		var place = 1
		val map = mutableMapOf<String, DbEntrant.() -> Unit>()

		race.entrants
				.filter { it.user.id in match.entrants.map { e -> e.userId } }
				.sortedBy { it.place }
				.forEach {
					map[it.user.id] = {
						finishTime = it.finishTime
						racetimePlace = if (it.status == DONE) place++ else null
						state = if (it.status == DONE) EntrantState.FINISHED else EntrantState.DID_NOT_FINISH
					}
				}

		return map.toMap()
	}

	sealed class RecordingException : RuntimeException()
	object RacetimeHttpErrorException : RecordingException()
	class RacetimeInconsistencyException(val reason: String) : RecordingException()
}
