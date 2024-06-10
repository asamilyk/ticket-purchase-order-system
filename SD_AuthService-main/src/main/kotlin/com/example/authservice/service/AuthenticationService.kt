package com.example.authservice.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import com.example.authservice.configuration.JwtProperties
import com.example.authservice.exception.ConflictException
import com.example.authservice.model.requests.AuthenticationRequest
import com.example.authservice.model.requests.RegistrationRequest
import com.example.authservice.model.responses.AuthenticationResponse
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val sessionService: SessionService
) {

    fun authentication(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authenticationRequest.email)

        val userEntity = userDetailsService.getUserByEmail(authenticationRequest.email)
        val accessToken = createAccessToken(user, userEntity.id)
        sessionService.createSession(userEntity, accessToken, getAccessTokenExpiration())

        return AuthenticationResponse(
            accessToken = accessToken,
        )
    }

    fun registerNewUser(user: RegistrationRequest) {
        val login = user.email
        if(userDetailsService.userWithLoginExists(login))
            throw ConflictException("User with email $login already exists")
        userDetailsService.createUser(user)
    }

    private fun createAccessToken(user: UserDetails, uuid: UUID) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration(),
        additionalClaims = mapOf("ID" to uuid)
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}