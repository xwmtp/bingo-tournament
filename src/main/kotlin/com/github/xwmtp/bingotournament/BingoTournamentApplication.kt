package com.github.xwmtp.bingotournament

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class BingoTournamentApplication

fun main(args: Array<String>) {
  runApplication<BingoTournamentApplication>(*args)
}
