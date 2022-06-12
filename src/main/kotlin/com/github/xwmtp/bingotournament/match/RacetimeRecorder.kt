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

			validate(this, race) ?: throw RacetimeInconsistencyException

			val raceEntrants = parseEntrants(this, race)

			scheduledTime = race.startedAt
			state = MatchState.FINISHED
			this.racetimeId = racetimeId
			entrants.onEach { raceEntrants[it.userId] }
		}
	}

	private fun validate(match: DbMatch, race: RacetimeRace): Unit? =
			if (!race.entrants.map { it.user.id }.containsAll(match.entrants.map { it.userId })) null
			else if (race.teamRace) null
			else if (race.status != RacetimeRace.RacetimeRaceStatus.FINISHED) null
			else if (!race.recorded) null
			else null

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
	object RacetimeInconsistencyException : RecordingException()
}
