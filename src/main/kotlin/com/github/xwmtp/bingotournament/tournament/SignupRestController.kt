package com.github.xwmtp.bingotournament.tournament

import com.github.xwmtp.api.SignupApi
import com.github.xwmtp.bingotournament.role.DbRole
import com.github.xwmtp.bingotournament.user.UserRepository
import com.github.xwmtp.bingotournament.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class SignupRestController(
		private val userService: UserService,
		private val repository: UserRepository,
) : SignupApi {

	private val logger = LoggerFactory.getLogger(SignupRestController::class.java)

	override fun signUp(): ResponseEntity<Unit> {

		if (true) {
			return ResponseEntity.notFound().build()
		}

		val user = userService.getCurrentUser() ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
		logger.debug("Signup called for user: ${user.id}")

		if (DbRole.ENTRANT !in user.roles) {
			user.roles += DbRole.ENTRANT
			repository.save(user)
		}

		return ResponseEntity.noContent().build()
	}

	override fun withdraw(): ResponseEntity<Unit> {

		if (true) {
			return ResponseEntity.notFound().build()
		}

		val user = userService.getCurrentUser() ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
		logger.debug("Withdraw called for user: ${user.id}")

		if (DbRole.ENTRANT in user.roles) {
			user.roles -= DbRole.ENTRANT
			repository.save(user)
		}

		return ResponseEntity.noContent().build()
	}
}
