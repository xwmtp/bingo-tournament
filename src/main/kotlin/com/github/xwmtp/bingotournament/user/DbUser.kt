package com.github.xwmtp.bingotournament.user

import com.github.xwmtp.api.model.User
import com.github.xwmtp.bingotournament.role.DbRole
import java.net.URI
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id

@Entity
class DbUser(
		@Id
		var id: String = "",
		var username: String = "",
		var avatarUrl: String? = null,
		var twitchChannel: String? = null,
		@ElementCollection(fetch = FetchType.EAGER)
		internal var roleStrings: MutableSet<String> = mutableSetOf(),
) {

	var roles: Set<DbRole>
		get() = roleStrings.map { DbRole.valueOf(it) }.toSet()
		set(newRoles) {
			roleStrings = newRoles.map { it.name }.toMutableSet()
		}

	fun inApiFormat(): User = User(
			id,
			username,
			roles.map { it.toApiFormat() },
			avatarUrl?.let { URI.create(it) },
			twitchChannel?.let { URI.create(it) },
	)

	override fun toString() = "$username $roles"
}
