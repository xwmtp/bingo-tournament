package com.github.xwmtp.bingotournament.user

import com.github.xwmtp.bingotournament.role.DbRole
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository,
    private val properties: UserProperties,
) {

  fun getCurrentUser(): DbUser? =
      when (val principal = SecurityContextHolder.getContext().authentication.principal) {
        is TournamentOauthUser -> getOrCreateDbUser(principal)
        else -> null
      }

  private fun getOrCreateDbUser(oauthUser: TournamentOauthUser): DbUser =
      repository.findById(oauthUser.id) ?: repository.save(oauthUser.newDbUser().checkForAdmin())

  private fun DbUser.checkForAdmin(): DbUser {

    if (id in properties.admins) {
      roles = roles + DbRole.ADMIN
    }

    return this
  }
}
