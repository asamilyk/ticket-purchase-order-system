package com.authservice.service
import com.authservice.model.Session
import com.authservice.model.User
import com.authservice.repository.SessionRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

@Service
class AuthService(
    private val userService: UserService,
    private val sessionRepository: SessionRepository,
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val jwtExpiration: Long
) {
    fun authenticate(email: String, password: String): String? {
        val user = userService.findByEmail(email) ?: return null
        if (!BCryptPasswordEncoder().matches(password, user.password)) return null

        val token = generateToken(user)
        val session = Session(user = user, token = token, expires = Timestamp(System.currentTimeMillis() + jwtExpiration))
        sessionRepository.save(session)

        return token
    }

    fun generateToken(user: User): String {
        val now = Date()
        val validity = Date(now.time + jwtExpiration)

        return Jwts.builder()
            .setSubject(user.id.toString())
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            val claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            val session = sessionRepository.findByToken(token) ?: return false
            return !session.expires.before(Date())
        } catch (e: Exception) {
            return false
        }
    }

    fun getUserFromToken(token: String): User? {
        val userId = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject.toLong()
        return userService.findById(userId)
    }
}
