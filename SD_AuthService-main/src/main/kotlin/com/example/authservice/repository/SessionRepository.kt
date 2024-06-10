package com.example.authservice.repository

import com.example.authservice.model.domain.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Int> {
    fun findByToken(token: String): Session?
}
