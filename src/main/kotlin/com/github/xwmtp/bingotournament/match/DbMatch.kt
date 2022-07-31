package com.github.xwmtp.bingotournament.match

import com.github.xwmtp.api.model.Match
import com.github.xwmtp.api.model.MatchState
import com.github.xwmtp.bingotournament.user.DbUser
import java.net.URI
import java.time.Instant
import java.time.ZoneOffset
import java.util.*
import javax.persistence.*

@Entity
class DbMatch(
		@Id
		var id: UUID = UUID.randomUUID(),
		@OneToMany(cascade = [CascadeType.ALL], mappedBy = "id.match", fetch = FetchType.EAGER)
		var entrants: List<DbEntrant> = listOf(),
		@Enumerated(EnumType.STRING)
		var state: MatchState = MatchState.NEW,
		var round: String? = null,
		var scheduledTime: Instant? = null,
		var racetimeId: String? = null,
		var restreamChannel: String? = null,
		@ManyToOne(cascade = [], fetch = FetchType.EAGER) @JoinColumn(name = "user_id")
		var restreamUser: DbUser? = null,
) {

	fun inApiFormat() = Match(
			id = id,
			entrants = entrants.map { it.inApiFormat() },
			state = state,
			round = round,
			scheduledTime = scheduledTime?.atOffset(ZoneOffset.UTC),
			racetimeId = racetimeId,
			restreamChannel = restreamChannel?.let { URI.create(it) },
			restreamUser = restreamUser?.inApiFormat(),
	)
}
