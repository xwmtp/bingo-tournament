package com.github.xwmtp.bingotournament.user

import org.springframework.data.repository.Repository
import org.springframework.stereotype.Component

@Component
interface UserRepository : Repository<DbUser, String> {

	fun save(user: DbUser): DbUser
	fun findById(id: String): DbUser?
	fun findAll(): Collection<DbUser>
}
