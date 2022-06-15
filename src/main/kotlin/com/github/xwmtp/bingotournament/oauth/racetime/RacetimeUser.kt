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
			TournamentOauthUser(id, name, avatar.prependTldIfMissing(racetimeBaseUrl), twitchChannel)

	private fun URI?.prependTldIfMissing(tld: String) =
			if (this == null) null
			else if (!this.isAbsolute) URI.create("$tld$this")
			else this
}
