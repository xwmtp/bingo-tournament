package com.github.xwmtp.racescheduler.schedule

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class Endpoints(private val scheduleService: ScheduleService) {

    @PostMapping("/schedule")
    fun scheduleRace(@RequestBody race: Race): ResponseEntity<Race> {
        return ResponseEntity.ok(scheduleService.scheduleRace(race))
    }

    @GetMapping("/schedule")
    fun getSchedule(): ResponseEntity<List<Race>> {
        return ResponseEntity.ok(scheduleService.getFullSchedule())
    }
}
