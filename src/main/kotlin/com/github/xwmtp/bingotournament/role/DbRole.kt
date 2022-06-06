package com.github.xwmtp.bingotournament.role

import com.github.xwmtp.api.model.Role
import javax.persistence.Embeddable

const val ADMIN_ROLE = "ADMIN"
const val ENTRANT_ROLE = "ENTRANT"

@Embeddable
enum class DbRole(val role: String) {

	ADMIN(ADMIN_ROLE), ENTRANT(ENTRANT_ROLE);

	fun toApiFormat(): Role =
			when (this) {
				ADMIN -> Role.ADMIN
				ENTRANT -> Role.ENTRANT
			}
}
