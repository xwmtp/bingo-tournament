package com.github.xwmtp.bingotournament.util

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("bingo.frontend")
class FrontendProperties(
		var baseUrl: String = "",
		var corsUrl: String = "",
)
