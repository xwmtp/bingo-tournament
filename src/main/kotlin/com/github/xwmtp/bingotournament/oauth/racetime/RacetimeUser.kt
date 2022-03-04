package com.github.xwmtp.bingotournament.oauth.racetime

import com.github.xwmtp.bingotournament.user.TournamentOauthUser
import com.google.gson.annotations.SerializedName
import java.net.URI

data class RacetimeUser(
    val id: String,
    val name: String,
    val avatar: URI?,
    @SerializedName("twitch_channel")
    val twitchChannel: URI?,
) {

  fun asOauthUser(racetimeBaseUrl: String) =
      TournamentOauthUser(id, name, avatar?.let { URI.create("${racetimeBaseUrl}$it") }, twitchChannel)
}
