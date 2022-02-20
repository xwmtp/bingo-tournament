package com.github.xwmtp.bingotournament.oauth

import com.github.xwmtp.bingotournament.oauth.racetime.RacetimeOauthClient
import com.github.xwmtp.bingotournament.oauth.racetime.RacetimeUserService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint

@Configuration
class OauthConfiguration(
    private val oauthProperties: OauthProperties,
    private val racetimeOauthClient: RacetimeOauthClient,
    private val racetimeUserService: RacetimeUserService,
) : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {

    http.exceptionHandling().authenticationEntryPoint(Http403ForbiddenEntryPoint())

    http.oauth2Login()
        .authorizationEndpoint().baseUri("/login")
        .and().tokenEndpoint().accessTokenResponseClient(racetimeOauthClient)
        .and().userInfoEndpoint().userService(racetimeUserService)

    http.authorizeRequests()
        .antMatchers("/api/health", "/login").permitAll()
        .anyRequest().authenticated()
  }
}
