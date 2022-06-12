package com.github.xwmtp.bingotournament.util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.client.RestTemplate

@Configuration
class GlobalConfiguration {

	@Bean
	@Primary
	fun restTemplate() = RestTemplate()
}
