package com.korus.core.application

import com.korus.core.domain.User
import java.time.LocalDateTime
import java.util.UUID

interface UserRepository {
    fun save(user: User)
    fun findAll(
        userId: UUID? = null,
        name: String? = null,
        email: String? = null,
        createdAt: LocalDateTime? = null,
    ): List<User>
    fun findByEmail(email: String): User?

}