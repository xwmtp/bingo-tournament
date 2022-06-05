package com.github.xwmtp.bingotournament.util

inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T = if (condition) this.apply(block) else this

inline fun <T> Iterable<T>.filterIf(condition: Boolean, predicate: (T) -> Boolean): Iterable<T> =
		if (condition) filter(predicate) else this
