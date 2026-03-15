package com.korus.core.api

import com.korus.core.application.CreateUserUseCase
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val createUserUseCase: CreateUserUseCase
) {
    @PostMapping("/register")
    fun register(@RequestBody request: CreateUserRequest): UserResponse {
        val user = createUserUseCase.execute(request)
        return UserResponse(
            id = user.userId,
            name = user.name,
            email = user.email,
            createdAt = user.createdAt,
            phoneNumber = user.phoneNumber
        )
    }
}