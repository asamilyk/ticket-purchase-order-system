package com.example.authservice.service

import com.example.authservice.exception.NotFoundException
import com.example.authservice.model.requests.RegistrationRequest
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import com.example.authservice.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID;

typealias ApplicationUser = com.example.authservice.model.domain.User

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not found!")

    fun userWithLoginExists(login: String): Boolean {
        return userRepository.findByEmail(login) != null
    }

    fun getUserByEmail(email: String): ApplicationUser =
        userRepository.findByEmail(email) ?: throw NotFoundException("No user with email: $email")

    fun createUser(request: RegistrationRequest): UUID {
        var encodedPassword = passwordEncoder.encode(request.password)
        val user: ApplicationUser = ApplicationUser(
            userName = request.username,
            email = request.email,
            password =  encodedPassword,
        )
        val created = userRepository.save(user)
        return created.id
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.email)
            .password(this.password)
            .build()
}