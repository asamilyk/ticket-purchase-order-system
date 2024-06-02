package com.authservice.service
import com.authservice.model.User
import com.authservice.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun register(user: User): User {
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)
    fun findById(id: Long): User? = userRepository.getReferenceById(id)
}
