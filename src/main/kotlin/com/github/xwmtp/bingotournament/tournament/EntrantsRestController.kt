package com.github.xwmtp.bingotournament.tournament

import com.github.xwmtp.api.EntrantsApi
import com.github.xwmtp.api.model.User
import com.github.xwmtp.bingotournament.role.DbRole
import com.github.xwmtp.bingotournament.user.UserRepository
import com.github.xwmtp.bingotournament.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class EntrantsRestController(
		private val userService: UserService,
		private val repository: UserRepository,
) : EntrantsApi {

	override fun getEntrants(): ResponseEntity<List<User>> {

		(userService.getCurrentUser() ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
				.roles.takeIf { it.contains(DbRole.ADMIN) }
				?: return ResponseEntity.status(HttpStatus.FORBIDDEN).build()

		return ResponseEntity.ok(
				repository.findAll()
						.filter { DbRole.ENTRANT in it.roles }
						.map { it.inApiFormat() }
		)
	}
}
