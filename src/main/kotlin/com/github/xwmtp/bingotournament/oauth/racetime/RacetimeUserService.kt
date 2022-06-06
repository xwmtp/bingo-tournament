package com.github.xwmtp.bingotournament.oauth.racetime

import com.github.xwmtp.bingotournament.oauth.OauthProperties
import com.github.xwmtp.bingotournament.role.DbRole
import com.github.xwmtp.bingotournament.user.DbUser
import com.github.xwmtp.bingotournament.user.TournamentOauthUser
import com.github.xwmtp.bingotournament.user.UserProperties
import com.github.xwmtp.bingotournament.user.UserRepository
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import org.springframework.web.client.RestOperations
import org.springframework.web.client.exchange

@Component
class RacetimeUserService(
		private val restOperations: RestOperations,
		private val properties: OauthProperties,
		private val repository: UserRepository,
		private val userProperties: UserProperties,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User? {

		val userInfoUri = userRequest.clientRegistration.providerDetails.userInfoEndpoint.uri

		val headers = HttpHeaders().apply {
			set(HttpHeaders.AUTHORIZATION, "Bearer ${userRequest.accessToken.tokenValue}")
			set(HttpHeaders.USER_AGENT, "oot-bingo-tournament")
		}.let { HttpEntity<Map<String, String>>(it) }

		return restOperations
				.exchange<RacetimeUser>(userInfoUri, HttpMethod.GET, headers)
				.body
				?.asOauthUser(properties.racetime.baseUrl)
				?.apply { addRoles(getOrCreateDbUser(this).roles) }
	}

	private fun getOrCreateDbUser(oauthUser: TournamentOauthUser): DbUser =
			repository.findById(oauthUser.id) ?: repository.save(oauthUser.newDbUser().checkForAdmin())

	private fun DbUser.checkForAdmin(): DbUser {

		if (id in userProperties.admins) {
			roles = roles + DbRole.ADMIN
		}

		return this
	}
}
