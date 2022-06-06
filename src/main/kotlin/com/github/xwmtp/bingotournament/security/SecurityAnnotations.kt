package com.github.xwmtp.bingotournament.security

import com.github.xwmtp.bingotournament.role.ADMIN_ROLE
import com.github.xwmtp.bingotournament.role.ENTRANT_ROLE
import org.springframework.security.access.annotation.Secured

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Secured(ADMIN_ROLE)
annotation class AdminOnly

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Secured(ADMIN_ROLE, ENTRANT_ROLE)
annotation class EntrantOnly
