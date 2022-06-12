package com.github.xwmtp.bingotournament.match

import com.github.xwmtp.api.model.Entrant
import com.github.xwmtp.api.model.EntrantState
import com.github.xwmtp.bingotournament.user.DbUser
import java.io.Serializable
import java.time.Duration
import javax.persistence.*

@Entity
class DbEntrant(
		@EmbeddedId var id: DbEntrantId,
		@Enumerated(EnumType.STRING) var state: EntrantState = EntrantState.PRE_RACE,
		var finishTime: Duration? = null,
		var racetimePlace: Int? = null,
) {

	fun inApiFormat() = Entrant(
			user = id.user.inApiFormat(),
			state = state,
			finishTime = finishTime?.toString(),
			finishTimeSeconds = finishTime?.toSeconds(),
			racetimePlace = racetimePlace,
	)

	val userId: String
		get() = id.user.id

	@ManyToOne
	@JoinColumn(name = "db_match_id")
	var dbMatch: DbMatch? = null
}

@Embeddable
class DbEntrantId(
		@ManyToOne(cascade = [], fetch = FetchType.EAGER) @JoinColumn(name = "user_id") var user: DbUser,
		@ManyToOne(cascade = [], fetch = FetchType.EAGER) @JoinColumn(name = "match_id") var match: DbMatch,
) : Serializable
