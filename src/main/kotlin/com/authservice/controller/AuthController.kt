package com.authservice.controller

import com.authservice.model.User
import com.authservice.service.AuthService
import com.authservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService,
    private val authService: AuthService
) {
    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseEntity<String> {
        if (userService.findByEmail(user.email) != null) {
            return ResponseEntity("Email already in use", HttpStatus.BAD_REQUEST)
        }
        userService.register(user)
        return ResponseEntity("User registered successfully", HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestParam email: String, @RequestParam password: String): ResponseEntity<Any> {
        val token = authService.authenticate(email, password) ?: return ResponseEntity("Invalid email or password", HttpStatus.UNAUTHORIZED)
        return ResponseEntity(mapOf("token" to token), HttpStatus.OK)
    }

    @GetMapping("/user")
    fun getUser(@RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        if (!authService.validateToken(token)) return ResponseEntity("Invalid token", HttpStatus.UNAUTHORIZED)
        val user = authService.getUserFromToken(token) ?: return ResponseEntity("User not found", HttpStatus.NOT_FOUND)
        return ResponseEntity(user, HttpStatus.OK)
    }
}
