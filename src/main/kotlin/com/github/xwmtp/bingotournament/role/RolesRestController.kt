package com.github.xwmtp.bingotournament.role

import com.github.xwmtp.api.RoleApi
import com.github.xwmtp.api.model.Role
import com.github.xwmtp.bingotournament.security.AdminOnly
import com.github.xwmtp.bingotournament.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class RolesRestController(private val userRepository: UserRepository) : RoleApi {

	private val logger = LoggerFactory.getLogger(RolesRestController::class.java)

	@AdminOnly
	override fun addRole(userId: String, role: Role): ResponseEntity<Unit> {

		try {

			validateRole(role) ?: return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
			val user = userRepository.findById(userId) ?: return ResponseEntity.notFound().build()

			user.roles = user.roles + DbRole.fromApiFormat(role)
			userRepository.save(user)

			return ResponseEntity.noContent().build()
		} catch (e: Exception) {
			logger.error("Failed to add role {} to user {}", role, userId, e)
			return ResponseEntity.internalServerError().build()
		}
	}

	@AdminOnly
	override fun removeRole(userId: String, role: Role): ResponseEntity<Unit> {

		try {

			validateRole(role) ?: return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
			val user = userRepository.findById(userId) ?: return ResponseEntity.notFound().build()

			user.roles = user.roles - DbRole.fromApiFormat(role)
			userRepository.save(user)

			return ResponseEntity.noContent().build()
		} catch (e: Exception) {
			logger.error("Failed to add role {} to user {}", role, userId, e)
			return ResponseEntity.internalServerError().build()
		}
	}

	private fun validateRole(role: Role): Unit? =
			if (role == Role.ADMIN) null
			else Unit
}
