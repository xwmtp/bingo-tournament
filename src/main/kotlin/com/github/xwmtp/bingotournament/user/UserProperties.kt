package com.github.xwmtp.bingotournament.user

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("bingo.users")
data class UserProperties(
    var admins: List<String> = listOf(),
)
