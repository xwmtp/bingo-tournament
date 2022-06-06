package com.github.xwmtp.bingotournament.role

import com.github.xwmtp.api.model.Role
import javax.persistence.Embeddable

const val ADMIN_ROLE = "ROLE_ADMIN"
const val ENTRANT_ROLE = "ROLE_ENTRANT"

@Embeddable
enum class DbRole(val role: String) {

	ADMIN(ADMIN_ROLE), ENTRANT(ENTRANT_ROLE);

	fun toApiFormat(): Role =
			when (this) {
				ADMIN -> Role.ADMIN
				ENTRANT -> Role.ENTRANT
			}
}