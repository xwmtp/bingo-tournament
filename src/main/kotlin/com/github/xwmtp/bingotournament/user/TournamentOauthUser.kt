package com.github.xwmtp.bingotournament.user

import com.github.xwmtp.api.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.io.Serializable
import java.net.URI

class TournamentOauthUser(
    private val id: String,
    private val username: String,
    private val avatarUrl: URI?,
    private val twitchChannel: URI?,
) : OAuth2User, Serializable {

  override fun getName(): String = username

  override fun getAttributes(): MutableMap<String, Any> = mutableMapOf()

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableSetOf()

  fun inApiFormat() = User(id, username, avatarUrl, twitchChannel)
}
