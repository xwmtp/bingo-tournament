package com.github.xwmtp.racescheduler.schedule

import org.springframework.data.repository.Repository
import java.util.*

@org.springframework.stereotype.Repository
interface ScheduleRepository : Repository<Race, UUID> {

    fun save(race: Race): Race

    fun findAll(): List<Race>


}