package com.github.xwmtp.bingotournament.match.racetime_client

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class RacetimeHttpClient(
		private val racetimeRestTemplate: RestTemplate,
		private val properties: RacetimeApiProperties,
) {

	private val logger = LoggerFactory.getLogger(RacetimeHttpClient::class.java)

	fun getRace(racetimeId: String): RacetimeRace? =
			racetimeRestTemplate
					.getForEntity(URI.create("${properties.baseUrl}/$racetimeId/data"), RacetimeRace::class.java)
					.also {
						if (it.statusCode == HttpStatus.OK) {
							logger.info("Loaded race '$racetimeId'")
						} else {
							logger.error("Failed to load race '$racetimeId'. Status: ${it.statusCodeValue}")
						}
					}.body
}
