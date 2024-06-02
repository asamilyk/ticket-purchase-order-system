package com.authservice.repository
import com.authservice.model.Session
import org.springframework.data.jpa.repository.JpaRepository

interface SessionRepository : JpaRepository<Session, Long> {
    fun findByToken(token: String): Session?
}
