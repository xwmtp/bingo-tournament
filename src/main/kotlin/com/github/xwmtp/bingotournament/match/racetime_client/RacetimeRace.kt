package com.github.xwmtp.bingotournament.match.racetime_client

import java.time.Instant

data class RacetimeRace(
		var status: RacetimeRaceStatus = RacetimeRaceStatus.OPEN,
		var entrants: List<RacetimeEntrant> = emptyList(),
		var startedAt: Instant? = null,
		var recorded: Boolean = false,
		var teamRace: Boolean = false,
) {

	enum class RacetimeRaceStatus {
		OPEN, INVITATIONAL, PENDING, IN_PROGRESS, FINISHED, CANCELLED
	}
}
