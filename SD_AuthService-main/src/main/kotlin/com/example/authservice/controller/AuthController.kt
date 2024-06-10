package com.example.authservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.example.authservice.model.requests.AuthenticationRequest
import com.example.authservice.model.requests.RegistrationRequest
import com.example.authservice.model.responses.AuthenticationResponse
import com.example.authservice.service.AuthenticationService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated

@RestController
@RequestMapping("/api/auth")
@Validated
class AuthController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping
    @Operation(summary = "Authenticate user")
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest
    ) : AuthenticationResponse =
        authenticationService.authentication(authRequest)


    @PostMapping("/register", consumes = ["application/json"], produces = ["application/json"])
    @Operation(summary = "Register new user")
    fun registerUser(@RequestBody @Valid registrationRequest: RegistrationRequest): ResponseEntity<Map<String, String>> {
        authenticationService.registerNewUser(registrationRequest)
        return ResponseEntity.ok(mapOf("message" to "Successfully registered"))
    }
}
