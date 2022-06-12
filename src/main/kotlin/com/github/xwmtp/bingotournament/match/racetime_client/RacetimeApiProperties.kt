package com.github.xwmtp.bingotournament.match.racetime_client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("gg.racetime.api")
data class RacetimeApiProperties(
		var baseUrl: String = "",
)
