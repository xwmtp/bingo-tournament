package com.github.xwmtp.bingotournament.user

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository,
) {

  fun getCurrentUser(): DbUser? =
      when (val principal = SecurityContextHolder.getContext().authentication.principal) {
        is TournamentOauthUser -> repository.findById(principal.id)
        else -> null
      }
}
