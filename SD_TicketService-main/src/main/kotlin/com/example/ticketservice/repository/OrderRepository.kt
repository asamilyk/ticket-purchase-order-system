package com.example.ticketservice.repository

import com.example.ticketservice.model.domain.Order
import com.example.ticketservice.model.domain.Session
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Int> {
    fun findAllByStatus(status: Int): List<Order>
}