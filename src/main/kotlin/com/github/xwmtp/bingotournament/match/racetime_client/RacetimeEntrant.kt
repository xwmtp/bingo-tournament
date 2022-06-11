package com.github.xwmtp.bingotournament.match.racetime_client

import com.github.xwmtp.bingotournament.oauth.racetime.RacetimeUser
import java.time.Duration

data class RacetimeEntrant(
		var user: RacetimeUser = RacetimeUser("", "", null, null),
		var finishTime: Duration? = null,
		var place: Int? = null,
		var status: RacetimeEntrantStatus? = null,
) {

	enum class RacetimeEntrantStatus {
		REQUESTED, INVITED, DECLINED, READY, NOT_READY, IN_PROGRESS, DONE, DNF, DQ
	}
}
