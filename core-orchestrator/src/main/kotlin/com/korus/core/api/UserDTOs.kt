package com.korus.core.api

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime
import java.util.UUID

data class CreateUserRequest(
    @field:NotBlank(message = "El nombre no puede estar vacío")
    val name: String,

    @field:Email(message = "Debe ser un formato de correo electrónico válido")
    val email: String,

    val phoneNumber: String,

    @field:Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres para ser segura")
    val password: String
)

data class UserResponse(val id: UUID, val name: String, val email: String, val createdAt: LocalDateTime, val phoneNumber: String)