package com.github.xwmtp.bingotournament.user

import com.github.xwmtp.api.UserApi
import com.github.xwmtp.api.model.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserRestController(private val userService: UserService) : UserApi {

	override fun getUser(): ResponseEntity<User> =
			userService.getCurrentUser()
					?.let { ResponseEntity.ok(it.inApiFormat()) }
					?: ResponseEntity.notFound().build()
}
