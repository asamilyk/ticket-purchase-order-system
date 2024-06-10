package com.example.ticketservice.model.enums

enum class OrderStatus(val status: Int) {
    CHECK(1),
    SUCCESS(2),
    REJECTION(3);
    companion object {
        fun getByStatus(status: Int) = values().firstOrNull { it.status == status }
    }
}