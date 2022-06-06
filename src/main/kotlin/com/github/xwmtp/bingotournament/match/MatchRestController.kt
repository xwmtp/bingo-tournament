package com.github.xwmtp.bingotournament.match

import com.github.xwmtp.api.MatchesApi
import com.github.xwmtp.api.model.Match
import com.github.xwmtp.api.model.MatchState
import com.github.xwmtp.api.model.NewMatch
import com.github.xwmtp.api.model.UpdateMatch
import com.github.xwmtp.bingotournament.security.AdminOnly
import com.github.xwmtp.bingotournament.security.EntrantOnly
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class MatchRestController(private val service: MatchService) : MatchesApi {

	@AdminOnly
	override fun addMatches(matches: List<NewMatch>): ResponseEntity<List<Match>> =
			matches.mapNotNull { service.addNewMatch(it) }
					.takeUnless { it.isEmpty() }
					?.let { ResponseEntity.ok(it) }
					?: ResponseEntity.internalServerError().build()

	@AdminOnly
	override fun deleteMatches(matchIds: List<UUID>): ResponseEntity<Unit> =
			service.deleteAllMatches(matchIds).takeIf { it }
					?.let { ResponseEntity.noContent().build() }
					?: ResponseEntity.notFound().build()

	override fun getAllMatches(filter: MatchState?, onlyLoggedIn: Boolean): ResponseEntity<List<Match>> =
			ResponseEntity.ok(service.getAllMatches(filter, onlyLoggedIn))

	@EntrantOnly
	override fun updateMatch(matchId: UUID, match: UpdateMatch): ResponseEntity<Match> =
			when (val result = service.updateMatch(matchId, match)) {
				is MatchService.UpdatedSuccessfully -> ResponseEntity.ok(result.updatedMatch)
				MatchService.MatchNotFound -> ResponseEntity.notFound().build()
				MatchService.RacetimeInconsistency -> ResponseEntity.unprocessableEntity().build()
				MatchService.InsufficientRights -> ResponseEntity.status(HttpStatus.FORBIDDEN).build()
			}
}
