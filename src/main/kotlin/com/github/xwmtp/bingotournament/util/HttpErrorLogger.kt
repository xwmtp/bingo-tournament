package com.github.xwmtp.bingotournament.util

import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

@RestController
class HttpErrorLogger : ErrorController {

	private val logger = LoggerFactory.getLogger(HttpErrorLogger::class.java)

	@RequestMapping("/error")
	fun error(request: HttpServletRequest): ResponseEntity<Unit> {

		val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)
				.let { HttpStatus.valueOf(it as Int) }

		val path = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI) as String

		logger.error("{} {} - Error {}", request.method, path, status.value())

		return ResponseEntity.status(status).build()
	}
}
