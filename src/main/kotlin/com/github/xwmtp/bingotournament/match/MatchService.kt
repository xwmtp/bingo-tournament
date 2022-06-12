package com.github.xwmtp.bingotournament.match

import com.github.xwmtp.api.model.Match
import com.github.xwmtp.api.model.MatchState
import com.github.xwmtp.api.model.NewMatch
import com.github.xwmtp.api.model.UpdateMatch
import com.github.xwmtp.bingotournament.role.DbRole
import com.github.xwmtp.bingotournament.user.UserRepository
import com.github.xwmtp.bingotournament.user.UserService
import com.github.xwmtp.bingotournament.util.applyIf
import com.github.xwmtp.bingotournament.util.filterIf
import com.github.xwmtp.bingotournament.util.takeIfExactlyOnePropertySet
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

@Service
class MatchService(
		private val repository: MatchRepository,
		private val userRepository: UserRepository,
		private val userService: UserService,
		private val racetimeRecorder: RacetimeRecorder,
) {

	private val logger = LoggerFactory.getLogger(MatchService::class.java)

	@Transactional(rollbackFor = [Exception::class])
	fun addNewMatch(newMatch: NewMatch): Match? {

		if (newMatch.entrantIds.size < 2) {
			logger.error("Cannot create race with less than two entrants")
			return null
		}

		val match = DbMatch(
				round = newMatch.round,
		).let { repository.save(it) }

		val entrants = newMatch
				.entrantIds
				.mapNotNull { userRepository.findById(it) }
				.map { DbEntrant(DbEntrantId(it, match)) }

		if (entrants.size != newMatch.entrantIds.size) {
			logger.error("At least one user of new match not found: {}", newMatch)
			repository.delete(match)
			return null
		}

		return repository.save(match.apply { this.entrants = entrants }).inApiFormat()
	}

	fun deleteAllMatches(matchIds: Collection<UUID>): Boolean =
			matchIds
					.asSequence()
					.map { repository.findById(it) }
					.takeIf { null !in it }
					?.forEach { repository.delete(it!!) }
					?.let { true }
					?: false

	fun updateMatch(matchId: UUID, match: UpdateMatch): MatchUpdateResult {

		val currentUser = userService.getCurrentUser() ?: return InsufficientRights
		val savedMatch = repository.findById(matchId) ?: return MatchNotFound

		match.takeIfExactlyOnePropertySet() ?: return MatchInconsistency

		if (DbRole.ADMIN !in currentUser.roles && currentUser.id !in savedMatch.entrants.map { it.id.user.id }) {
			return InsufficientRights
		}

		return savedMatch
				.applyIf(match.containsValidScheduledTime()) {
					if (savedMatch.racetimeId != null) {
						return RacetimeInconsistency
					}
					scheduledTime = match.scheduledTime!!.toInstant()
					state = MatchState.SCHEDULED
				}
				.applyIf(match.racetimeId != null) {
					if (savedMatch.racetimeId != null && DbRole.ADMIN !in currentUser.roles) {
						return InsufficientRights
					}
					try {
						racetimeRecorder.recordRace(match.racetimeId!!).invoke(this)
					} catch (e: RacetimeRecorder.RecordingException) {
						return when (e) {
							RacetimeRecorder.RacetimeHttpErrorException -> ProxyError
							is RacetimeRecorder.RacetimeInconsistencyException -> {
								logger.error("$racetimeId can't be recorded: ${e.reason}")
								RacetimeInconsistency
							}
						}
					}
				}
				.let { repository.save(it) }
				.let { UpdatedSuccessfully(it.inApiFormat()) }
	}

	private fun UpdateMatch.containsValidScheduledTime() =
			scheduledTime != null && scheduledTime.isAfter(OffsetDateTime.now())

	fun getAllMatches(filter: MatchState?, onlyLoggedIn: Boolean): List<Match> =
			repository.findAll()
					.filterIf(filter != null) { it.state == filter }
					.filterIf(onlyLoggedIn) { userService.getCurrentUser()?.id in it.entrants.map { e -> e.id.user.id } }
					.map { it.inApiFormat() }

	sealed class MatchUpdateResult
	class UpdatedSuccessfully(val updatedMatch: Match) : MatchUpdateResult()
	object MatchNotFound : MatchUpdateResult()
	object MatchInconsistency : MatchUpdateResult()
	object RacetimeInconsistency : MatchUpdateResult()
	object ProxyError : MatchUpdateResult()
	object InsufficientRights : MatchUpdateResult()
}
