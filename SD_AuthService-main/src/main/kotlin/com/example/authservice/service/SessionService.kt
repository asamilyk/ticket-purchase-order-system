package com.example.authservice.service

import com.example.authservice.model.domain.Session
import com.example.authservice.model.domain.User
import com.example.authservice.repository.SessionRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SessionService(
    private val sessionRepository: SessionRepository
) {
    fun createSession(user: User, token: String, expires: Date): Session {
        val session = Session(user = user, token = token, expires = expires)
        return sessionRepository.save(session)
    }

    fun findSessionByToken(token: String): Session? {
        return sessionRepository.findByToken(token)
    }
}
