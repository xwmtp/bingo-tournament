package com.github.xwmtp.racescheduler.schedule

import org.springframework.stereotype.Service

@Service
class ScheduleService(private val scheduleRepository: ScheduleRepository) {

    fun getFullSchedule(): List<Race> {
        val races = scheduleRepository.findAll()
        return races.sortedBy { it.startTime }
    }

    fun scheduleRace(newRace: NewRace): Race {
        return scheduleRepository.save(toRaceEntity(newRace))
    }

    fun updateRace(race: Race): Race? {
        val scheduledRace = scheduleRepository.findById(race.id)
        if (scheduledRace === null) {
            return null
        }
        return scheduleRepository.save(race)
    }
}
