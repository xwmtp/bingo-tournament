package com.github.xwmtp.bingotournament.match.racetime_client

import com.google.gson.*
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.time.Instant

@Configuration
class RacetimeHttpClientConfiguration {

	@Bean
	fun racetimeRestTemplate(): RestTemplate = RestTemplateBuilder()
			.messageConverters(GsonHttpMessageConverter(racetimeGson()), StringHttpMessageConverter())
			.build()

	@Bean
	fun racetimeGson(): Gson = GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(Duration::class.java, durationDeserializer)
			.registerTypeAdapter(Duration::class.java, durationSerializer)
			.registerTypeAdapter(Instant::class.java, instantDeserializer)
			.registerTypeAdapter(Instant::class.java, instantSerializer)
			.registerTypeAdapter(RacetimeEntrant.RacetimeEntrantStatus::class.java, entrantStatusDeserializer)
			.registerTypeAdapter(RacetimeRace.RacetimeRaceStatus::class.java, raceStatusDeserializer)
			.registerTypeAdapter(RacetimeRace.RacetimeRaceStatus::class.java, raceStatusSerializer)
			.create()

	private val durationDeserializer = JsonDeserializer { json, _, _ ->
		Duration.parse(json.asString)
	}

	private val durationSerializer = JsonSerializer<Duration> { duration, _, _ ->
		JsonPrimitive(duration.toString())
	}

	private val instantDeserializer = JsonDeserializer { json, _, _ ->
		Instant.parse(json.asString)
	}

	private val instantSerializer = JsonSerializer<Instant> { instant, _, _ ->
		JsonPrimitive(instant.toString())
	}

	private val entrantStatusDeserializer = JsonDeserializer { json, _, _ ->
		RacetimeEntrant.RacetimeEntrantStatus
				.values()
				.find { it.name.equals(json.asJsonObject.get("value").asString, true) }
	}

	private val raceStatusDeserializer = JsonDeserializer { json, _, _ ->
		RacetimeRace.RacetimeRaceStatus
				.values()
				.find { it.name.equals(json.asJsonObject.get("value").asString, true) }
	}

	private val raceStatusSerializer = JsonSerializer<RacetimeRace.RacetimeRaceStatus> { status, _, _ ->
		JsonObject().apply { addProperty("value", status.name) }
	}
}
