package com.github.xwmtp.racescheduler.schedule

import org.springframework.stereotype.Service

@Service
class ScheduleService(private val scheduleRepository: ScheduleRepository) {

    fun scheduleRace(race: Race): Race {
        return scheduleRepository.save(race)
    }

    fun getFullSchedule(): List<Race> {
        val races = scheduleRepository.findAll()
        return races.sortedBy { it.startTime }
    }
}
