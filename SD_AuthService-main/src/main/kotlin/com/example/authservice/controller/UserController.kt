package com.example.authservice.controller

import com.example.authservice.exception.BadRequestException
import com.example.authservice.exception.ForbiddenException
import com.example.authservice.model.responses.UserResponse
import com.example.authservice.service.CustomUserDetailsService
import com.example.authservice.service.SessionService
import com.example.authservice.service.TokenService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/user")
@Validated
class UserController(
    private val jwtTokenService: TokenService,
    private val sessionService: SessionService,
    private val customUserDetailsService: CustomUserDetailsService
) {

    @GetMapping("/info", produces = ["application/json"])
    @Operation(summary = "Get user information", security = [SecurityRequirement(name = "bearerAuth")])
    fun getUserInfo() : UserResponse {
        val token = jwtTokenService.getBearerTokenHeader()
        val jwtToken = token.substring(7) // Remove "Bearer " prefix

        // Check if the session exists
        val session = sessionService.findSessionByToken(jwtToken)
            ?: throw IllegalArgumentException("Invalid session")
        if(session.expires < Date())
            throw ForbiddenException("Session has expired")
        val email = jwtTokenService.extractEmail(jwtToken)
        if(email.isNullOrBlank())
            throw BadRequestException("Bad token provided")
        val user = customUserDetailsService.getUserByEmail(email)
        return UserResponse(
            userName = user.userName,
            email = user.email
        )
    }
}