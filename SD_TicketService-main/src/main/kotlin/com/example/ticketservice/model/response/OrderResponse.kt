package com.example.ticketservice.model.response

import com.example.ticketservice.model.enums.OrderStatus
import java.util.Date
import java.util.UUID

data class OrderResponse(
    val id: Int,
    val userId: UUID,
    val from: String,
    val to: String,
    val createdAt: Date,
    val status: OrderStatus,
)