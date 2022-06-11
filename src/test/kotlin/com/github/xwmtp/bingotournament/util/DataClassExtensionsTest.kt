package com.github.xwmtp.bingotournament.util

import com.github.xwmtp.api.model.UpdateMatch
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime

internal class DataClassExtensionsTest {

	@Test
	internal fun falseIfNoPropertyIsSet() {
		assertThat(UpdateMatch().hasExactlyOnePropertySet()).isFalse()
	}

	@Test
	internal fun trueIfFirstPropertySet() {
		assertThat(UpdateMatch(scheduledTime = OffsetDateTime.MIN).hasExactlyOnePropertySet()).isTrue()
	}

	@Test
	internal fun trueIfSecondPropertySet() {
		assertThat(UpdateMatch(racetimeId = "oot/bingo-1").hasExactlyOnePropertySet()).isTrue()
	}

	@Test
	internal fun falseIfTwoPropertiesSet() {
		assertThat(UpdateMatch(OffsetDateTime.MIN, "oot/123").hasExactlyOnePropertySet()).isFalse()
	}
}
