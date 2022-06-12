package com.github.xwmtp.bingotournament.oauth

import com.github.xwmtp.bingotournament.oauth.racetime.RacetimeOauthClient
import com.github.xwmtp.bingotournament.oauth.racetime.RacetimeUserService
import com.github.xwmtp.bingotournament.util.FrontendProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
class SecurityConfiguration(
		private val frontendProperties: FrontendProperties,
		private val racetimeOauthClient: RacetimeOauthClient,
		private val racetimeUserService: RacetimeUserService,
) {

	@Bean
	fun configure(http: HttpSecurity): SecurityFilterChain {

		http.exceptionHandling().authenticationEntryPoint(Http403ForbiddenEntryPoint())

		http.oauth2Login()
				.authorizationEndpoint().baseUri("/login")
				.and().tokenEndpoint().accessTokenResponseClient(racetimeOauthClient)
				.and().userInfoEndpoint().userService(racetimeUserService)
				.and().defaultSuccessUrl(frontendProperties.baseUrl, true)

		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

		http.authorizeRequests()
				.antMatchers("/api/health", "/login").permitAll()
				.antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/matches").permitAll()
				.antMatchers(HttpMethod.GET, "/api/entrants").permitAll()
				.anyRequest().authenticated()

		return http.build()
	}
}
