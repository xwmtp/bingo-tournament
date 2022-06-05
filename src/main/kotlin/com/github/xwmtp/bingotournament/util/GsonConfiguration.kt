package com.github.xwmtp.bingotournament.util

import com.google.gson.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Configuration
class GsonConfiguration {

	@Bean
	fun gson(): Gson =
			GsonBuilder()
					.registerTypeAdapter(OffsetDateTime::class.java, offsetDateTimeSerializer)
					.registerTypeAdapter(OffsetDateTime::class.java, offsetDateTimeDeserializer)
					.create()

	private val offsetDateTimeSerializer = JsonSerializer<OffsetDateTime> { time, _, _ ->
		JsonPrimitive(time.toInstant().toString())
	}

	private val offsetDateTimeDeserializer = JsonDeserializer { json, _, _ ->
		Instant.parse(json.asString).atOffset(ZoneOffset.UTC)
	}
}
