package com.github.xwmtp.racescheduler.schedule

import org.hibernate.annotations.GenericGenerator
import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Race(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
    )
    var id: UUID?,
    var entrant1: String,
    var entrant2: String,
    var startTime: Instant,
    var round: String?,
    var restream: String?,
)

