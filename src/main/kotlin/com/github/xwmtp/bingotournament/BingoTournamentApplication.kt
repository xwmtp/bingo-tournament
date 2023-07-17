package com.github.xwmtp.bingotournament

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@EnableScheduling
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ConfigurationPropertiesScan
class BingoTournamentApplication

fun main(args: Array<String>) {
	runApplication<BingoTournamentApplication>(*args)
}
