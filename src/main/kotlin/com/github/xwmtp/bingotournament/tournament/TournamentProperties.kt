package com.github.xwmtp.bingotournament.tournament

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("bingo.tournament")
data class TournamentProperties(
		val allowSignups: Boolean,
)
