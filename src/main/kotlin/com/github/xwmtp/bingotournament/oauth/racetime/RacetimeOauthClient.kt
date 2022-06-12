package com.github.xwmtp.bingotournament.oauth.racetime

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Component
class RacetimeOauthClient(
		private val genericRestTemplate: RestTemplate,
) : OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

	override fun getTokenResponse(authorizationGrantRequest: OAuth2AuthorizationCodeGrantRequest): OAuth2AccessTokenResponse? {

		val clientRegistration = authorizationGrantRequest.clientRegistration
		val tokenUri = clientRegistration.providerDetails.tokenUri

		val authorizationExchange = authorizationGrantRequest.authorizationExchange

		val tokenRequest: MultiValueMap<String, String> = LinkedMultiValueMap()
		tokenRequest.add("client_id", clientRegistration.clientId)
		tokenRequest.add("client_secret", clientRegistration.clientSecret)
		tokenRequest.add("grant_type", clientRegistration.authorizationGrantType.value)
		tokenRequest.add("code", authorizationExchange.authorizationResponse.code)
		tokenRequest.add("redirect_uri", authorizationExchange.authorizationRequest.redirectUri)
		tokenRequest.add("scope", java.lang.String.join(" ", authorizationGrantRequest.clientRegistration.scopes))

		val headers = HttpHeaders()
		headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
		headers.add(HttpHeaders.USER_AGENT, "oot-bingo-tournament")

		val token: RacetimeOauthToken = genericRestTemplate.exchange<RacetimeOauthToken>(
				tokenUri, HttpMethod.POST, HttpEntity(tokenRequest, headers)
		).body ?: return null

		return OAuth2AccessTokenResponse.withToken(token.accessToken)
				.tokenType(OAuth2AccessToken.TokenType.BEARER.takeIf { token.tokenType.equals("bearer", true) })
				.expiresIn(token.expiresIn)
				.refreshToken(token.refreshToken)
				.scopes(token.scope.split("\\s+").toSet())
				.build()
	}
}
