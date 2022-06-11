package com.github.xwmtp.bingotournament.match

class RacetimeRecorder {

	@Throws(RecordingException::class)
	fun recordRace(racetimeId: String): DbMatch.() -> Unit {
		TODO("Not yet implemented")
	}

	sealed class RecordingException : RuntimeException()
	object RacetimeHttpErrorException : RecordingException()
	object RacetimeInconsistencyException : RecordingException()
}
