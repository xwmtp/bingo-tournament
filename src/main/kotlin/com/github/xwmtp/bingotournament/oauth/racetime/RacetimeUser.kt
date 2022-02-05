package com.github.xwmtp.bingotournament.oauth.racetime

import com.google.gson.annotations.SerializedName
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User
import java.net.URI

data class RacetimeUser(
    val id: String,
    val name: String,
    val avatar: String,
    @SerializedName("twitch_channel")
    val twitchChannel: URI,
) {

  fun asOauthUser() = object : OAuth2User {
    override fun getName(): String = this@RacetimeUser.name

    override fun getAttributes(): MutableMap<String, Any> = mutableMapOf()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()
  }
}
