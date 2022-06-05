package com.github.xwmtp.bingotournament.role

import javax.persistence.Embeddable

const val ADMIN_ROLE = "ADMIN"
const val ENTRANT_ROLE = "ENTRANT"

@Embeddable
enum class DbRole(val role: String) {

	ADMIN(ADMIN_ROLE), ENTRANT(ENTRANT_ROLE)
}
