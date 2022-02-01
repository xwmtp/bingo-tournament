package com.github.xwmtp.bingotournament.schedule

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class Endpoints(private val scheduleService: ScheduleService) {

    @GetMapping("/schedule")
    fun getSchedule(): ResponseEntity<List<Race>> {
        return ResponseEntity.ok(scheduleService.getFullSchedule())
    }

    @PostMapping("/schedule/race")
    fun scheduleRace(@RequestBody newRace: NewRace): ResponseEntity<Race> {
        println(newRace)
        return ResponseEntity.ok(scheduleService.scheduleRace(newRace))
    }

    @PutMapping("/schedule/race")
    fun updateRace(@RequestBody race: Race): ResponseEntity<Race> {
        val updatedRace = scheduleService.updateRace(race) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(updatedRace)
    }
}
