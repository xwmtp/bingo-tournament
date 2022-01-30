package com.github.xwmtp.racescheduler.schedule

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

data class NewRace(
    val entrant1: String,
    val entrant2: String,
    val startTime: Instant,
    val round: String?,
    val restream: String?,
)

@Entity
class Race(
    @Id
    var id: UUID,
    var entrant1: String,
    var entrant2: String,
    var startTime: Instant,
    var round: String?,
    var restream: String?,
)

fun toRaceEntity(newRace: NewRace): Race {
    return Race(
        UUID.randomUUID(),
        newRace.entrant1,
        newRace.entrant2,
        newRace.startTime,
        newRace.round,
        newRace.restream
    )
}
