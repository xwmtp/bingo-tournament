package com.github.xwmtp.bingotournament.security

import com.github.xwmtp.bingotournament.util.FrontendProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfiguration(private val frontendProperties: FrontendProperties) : WebMvcConfigurer {

	override fun addCorsMappings(registry: CorsRegistry) {

		registry.addMapping("/api/**")
				.allowedOrigins(frontendProperties.corsUrl)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true)

		registry.addMapping("/logout")
				.allowedOrigins(frontendProperties.corsUrl)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true)
	}
}
