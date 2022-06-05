package com.github.xwmtp.bingotournament.oauth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("bingo.oauth")
data class OauthProperties(
		@NestedConfigurationProperty
		var racetime: RacetimeOauthProperties = RacetimeOauthProperties(),
) {

	@ConfigurationProperties
	data class RacetimeOauthProperties(
			var baseUrl: String = "",
	)
}
