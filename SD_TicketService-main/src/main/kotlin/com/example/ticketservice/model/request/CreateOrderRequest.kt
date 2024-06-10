package com.example.ticketservice.model.request

import io.swagger.v3.oas.annotations.Parameter

data class CreateOrderRequest(
    @field:Parameter(description = "Departure station name")
    val from: String,

    @field:Parameter(description = "Arrival station name")
    val to: String
)