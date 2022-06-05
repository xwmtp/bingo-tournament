package com.github.xwmtp.bingotournament.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.io.Serializable
import java.net.URI

class TournamentOauthUser(
    val id: String,
    private val username: String,
    private val avatarUrl: URI?,
    private val twitchChannel: URI?,
) : OAuth2User, Serializable {

  private val authorities: MutableList<GrantedAuthority> = mutableListOf()

  override fun getName(): String = username

  override fun getAttributes(): MutableMap<String, Any> = mutableMapOf()

  override fun getAuthorities(): MutableCollection<GrantedAuthority> = authorities

  fun addRoles(roles: Collection<String>) {
    roles.map { SimpleGrantedAuthority(it) }.forEach { authorities.add(it) }
  }

  fun newDbUser() = DbUser(
      id, username, avatarUrl.toString(), twitchChannel.toString(),
  )
}
