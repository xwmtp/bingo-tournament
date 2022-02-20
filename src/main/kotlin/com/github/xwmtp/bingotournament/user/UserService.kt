package com.github.xwmtp.bingotournament.user

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService {

  fun getCurrentUser(): TournamentOauthUser? =
      when (val principal = SecurityContextHolder.getContext().authentication.principal) {
        is TournamentOauthUser -> principal
        else -> null
      }
}
