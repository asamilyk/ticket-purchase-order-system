package com.example.ticketservice.repository

import com.example.ticketservice.model.domain.Session
import com.example.ticketservice.model.domain.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StationRepository : JpaRepository<Station, Int> {
    fun findByName(name: String): Station?
}