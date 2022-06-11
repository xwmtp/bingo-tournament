package com.github.xwmtp.bingotournament.match.racetime_client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@Component
@ConstructorBinding
@ConfigurationProperties("gg.racetime.api")
data class RacetimeApiProperties(
		val baseUrl: String,
)
