package com.example.ticketservice.service

import com.example.ticketservice.model.domain.Session
import com.example.ticketservice.repository.SessionRepository
import org.springframework.stereotype.Service

@Service
class SessionService(
    private val sessionRepository: SessionRepository
) {
    fun findSessionByToken(token: String): Session? {
        return sessionRepository.findByToken(token)
    }
}