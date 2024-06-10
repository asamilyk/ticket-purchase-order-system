package com.example.authservice.model.requests

data class AuthenticationRequest(
    val email: String,
    val password: String,
)