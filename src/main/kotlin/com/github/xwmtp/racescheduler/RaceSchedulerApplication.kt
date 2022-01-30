package com.github.xwmtp.racescheduler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RaceSchedulerApplication

fun main(args: Array<String>) {
	runApplication<RaceSchedulerApplication>(*args)
}
