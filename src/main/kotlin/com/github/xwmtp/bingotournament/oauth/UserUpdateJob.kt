package com.github.xwmtp.bingotournament.oauth

import com.github.xwmtp.bingotournament.match.racetime_client.RacetimeHttpClient
import com.github.xwmtp.bingotournament.user.DbUser
import com.github.xwmtp.bingotournament.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.Instant

@Component
@ConditionalOnProperty(name = ["bingo.users.update-job.enabled"], havingValue = "true")
class UserUpdateJob(
		private val repository: UserRepository,
		private val httpClient: RacetimeHttpClient,
) {

	private val logger = LoggerFactory.getLogger(UserUpdateJob::class.java)

	@Transactional(rollbackFor = [Throwable::class])
	@Scheduled(cron = "\${bingo.users.update-job.cron}")
	fun refreshUserData() {

		val startTime = Instant.now()
		logger.info("Starting UserUpdateJob...")

		val allUsers = repository.findAll()

		for (user in allUsers) {

			val racetimeUser = httpClient.getUser(user.id)

			if (racetimeUser != null && userChanged(user, racetimeUser)) {

				logger.info("Updating user ${racetimeUser.username}")

				user.avatarUrl = racetimeUser.avatarUrl
				user.twitchChannel = racetimeUser.twitchChannel
				user.username = racetimeUser.username

				repository.save(user)
			}
		}

		val duration = Duration.between(startTime, Instant.now())
		logger.info("UserUpdateJob ended after $duration")
	}

	private fun userChanged(dbUser: DbUser, racetimeUser: DbUser): Boolean =
			dbUser.avatarUrl != racetimeUser.avatarUrl ||
					dbUser.twitchChannel != racetimeUser.twitchChannel ||
					dbUser.username != racetimeUser.username
}
