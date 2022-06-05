package com.github.xwmtp.bingotournament.match

import org.springframework.data.repository.Repository
import org.springframework.stereotype.Component
import java.util.*

@Component
interface MatchRepository : Repository<DbMatch, UUID> {

  fun findAll(): Collection<DbMatch>
  fun findById(id: UUID): DbMatch?

  fun save(match: DbMatch): DbMatch

  fun delete(match: DbMatch)
}
