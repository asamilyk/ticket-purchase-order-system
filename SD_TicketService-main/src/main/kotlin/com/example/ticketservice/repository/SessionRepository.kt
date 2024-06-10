package com.example.ticketservice.repository

import com.example.ticketservice.model.domain.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SessionRepository : JpaRepository<Session, Int> {
    fun findByToken(token: String): Session?
}