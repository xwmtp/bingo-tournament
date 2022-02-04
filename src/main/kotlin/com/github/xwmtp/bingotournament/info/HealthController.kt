package com.github.xwmtp.bingotournament.info

import com.github.xwmtp.api.HealthApi
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController : HealthApi {

  override fun getHealth(): ResponseEntity<Unit> {
    return ResponseEntity.noContent().build()
  }
}
