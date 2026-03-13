package com.korus.core.application

import com.korus.core.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class CreateUserUseCase(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun execute(request: com.korus.core.api.CreateUserRequest): User {

        if (repository.findByEmail(request.email) != null) {
            throw IllegalArgumentException("Este email ya está en uso")
        }

        val user = User(
            userId = UUID.randomUUID(),
            name = request.name,
            email = request.email,
            phoneNumber = request.phoneNumber,
            passwordHash = passwordEncoder.encode(request.password),
            createdAt = LocalDateTime.now()
        )

        return repository.save(user)
    }
}