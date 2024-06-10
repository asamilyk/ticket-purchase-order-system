package com.example.authservice.model.requests

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegistrationRequest (
    @Schema(description = "Nickname")
    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val username: String,

    @Schema(description = "Login(email) of user")
    @field:Email(message = "Email should be valid (ex: superuser@mail.ru)")
    @field:NotBlank(message = "Email should not be empty")
    val email: String,

    @Schema(description = "Password")
    @field:Size(min = 8, message = "Password should contain at least 8 characters")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*(),.?\":{}|<>]).{8,}\$",
        message = "Password should contain at least: 1 lower latin char, 1 upper latin char, 1 digit and 1 special symbol"
    )
    val password: String
)