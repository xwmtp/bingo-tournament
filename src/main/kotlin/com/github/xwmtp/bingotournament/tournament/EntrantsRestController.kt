package com.github.xwmtp.bingotournament.tournament

import com.github.xwmtp.api.EntrantsApi
import com.github.xwmtp.api.model.User
import com.github.xwmtp.bingotournament.role.DbRole
import com.github.xwmtp.bingotournament.user.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class EntrantsRestController(private val repository: UserRepository) : EntrantsApi {

	override fun getEntrants(): ResponseEntity<List<User>> =
			ResponseEntity.ok(
					repository.findAll()
							.filter { DbRole.ENTRANT in it.roles }
							.map { it.inApiFormat() }
			)
}
