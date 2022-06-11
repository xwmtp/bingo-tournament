package com.github.xwmtp.bingotournament.util

import kotlin.reflect.full.memberProperties

inline fun <reified T : Any> T.hasExactlyOnePropertySet(): Boolean =
		T::class.memberProperties.count { it.getValue(this, it) != null } == 1

inline fun <reified T : Any> T.takeIfExactlyOnePropertySet(): T? =
		if (hasExactlyOnePropertySet()) this
		else null
