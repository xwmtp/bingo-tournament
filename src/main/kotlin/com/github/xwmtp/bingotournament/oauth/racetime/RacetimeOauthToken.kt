package com.github.xwmtp.bingotournament.oauth.racetime

import com.google.gson.annotations.SerializedName

class RacetimeOauthToken(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("refresh_token")
    val refreshToken: String,
    val scope: String,
)
