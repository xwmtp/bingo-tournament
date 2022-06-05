package com.github.xwmtp.bingotournament.util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class GlobalConfiguration {

	@Bean
	fun restTemplate() = RestTemplate()
}
