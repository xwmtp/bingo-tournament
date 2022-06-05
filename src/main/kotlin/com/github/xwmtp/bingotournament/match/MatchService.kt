package com.github.xwmtp.bingotournament.match

import com.github.xwmtp.api.model.Match
import com.github.xwmtp.api.model.MatchState
import com.github.xwmtp.api.model.NewMatch
import com.github.xwmtp.api.model.UpdateMatch
import com.github.xwmtp.bingotournament.role.DbRole
import com.github.xwmtp.bingotournament.user.UserRepository
import com.github.xwmtp.bingotournament.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class MatchService(
    private val repository: MatchRepository,
    private val userRepository: UserRepository,
    private val userService: UserService,
) {

  private val logger = LoggerFactory.getLogger(MatchService::class.java)

  fun addNewMatch(newMatch: NewMatch): Match? {

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

  fun updateMatch(match: UpdateMatch): MatchUpdateResult {

    val currentUser = userService.getCurrentUser() ?: return InsufficientRights
    val savedMatch = repository.findById(match.id) ?: return MatchNotFound

    if (DbRole.ADMIN !in currentUser.roles && currentUser.id !in savedMatch.entrants.map { it.id.user.id }) {
      return InsufficientRights
    }

    if (match.racetimeId != null) {
      TODO("Racetime handling missing")
    }

    return savedMatch
        .applyIf(match.scheduledTime != null) { scheduledTime = match.scheduledTime!!.toInstant() }
        .let { repository.save(it) }
        .let { UpdatedSuccessfully(it.inApiFormat()) }
  }

  fun getAllMatches(filter: MatchState?, onlyLoggedIn: Boolean): List<Match> =
      repository.findAll()
          .filterIf(filter != null) { it.state == filter }
          .filterIf(onlyLoggedIn) { userService.getCurrentUser()?.id in it.entrants.map { e -> e.id.user.id } }
          .map { it.inApiFormat() }

  sealed class MatchUpdateResult
  class UpdatedSuccessfully(val updatedMatch: Match) : MatchUpdateResult()
  object MatchNotFound : MatchUpdateResult()
  object RacetimeInconsistency : MatchUpdateResult()
  object InsufficientRights : MatchUpdateResult()
}

inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T = if (condition) this.apply(block) else this

inline fun <T> Iterable<T>.filterIf(condition: Boolean, predicate: (T) -> Boolean): Iterable<T> =
    if (condition) filter(predicate) else this
