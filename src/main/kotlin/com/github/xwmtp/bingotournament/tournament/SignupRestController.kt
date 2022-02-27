package com.github.xwmtp.bingotournament.tournament

import com.github.xwmtp.api.SignupApi
import com.github.xwmtp.bingotournament.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class SignupRestController(private val userService: UserService) : SignupApi {

  private val logger = LoggerFactory.getLogger(SignupRestController::class.java)

  override fun signUp(): ResponseEntity<Unit> {

    logger.info("Signup called for user: ${userService.getCurrentUser()}")

    return ResponseEntity.noContent().build()
  }
}
