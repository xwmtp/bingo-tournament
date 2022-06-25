package com.github.xwmtp.bingotournament.role

import com.github.xwmtp.api.model.Role
import javax.persistence.Embeddable

const val ADMIN_ROLE = "ROLE_ADMIN"
const val ENTRANT_ROLE = "ROLE_ENTRANT"
const val RESTREAMER_ROLE = "ROLE_RESTREAMER"

@Embeddable
enum class DbRole(val role: String) {

	ADMIN(ADMIN_ROLE), ENTRANT(ENTRANT_ROLE), RESTREAMER(RESTREAMER_ROLE);

	companion object {

		fun fromApiFormat(role: Role) =
				when (role) {
					Role.ADMIN -> ADMIN
					Role.ENTRANT -> ENTRANT
					Role.RESTREAMER -> RESTREAMER
				}
	}

	fun toApiFormat(): Role =
			when (this) {
				ADMIN -> Role.ADMIN
				ENTRANT -> Role.ENTRANT
				RESTREAMER -> Role.RESTREAMER
			}
}
