package com.example.ticketservice.controller

import com.example.ticketservice.exception.BadRequestException
import com.example.ticketservice.exception.ForbiddenException
import com.example.ticketservice.model.request.CreateOrderRequest
import com.example.ticketservice.model.response.OrderResponse
import com.example.ticketservice.service.OrderService
import com.example.ticketservice.service.SessionService
import com.example.ticketservice.service.TokenService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*


@RestController
@RequestMapping("/api/order")
@Validated
class OrderController(
    private val jwtTokenService: TokenService,
    private val sessionService: SessionService,
    private val orderService: OrderService
) {
    @PostMapping("/create", produces = ["application/json"])
    @Operation(summary = "Create new order", security = [SecurityRequirement(name = "bearerAuth")])
    fun createOrder(@RequestBody request: CreateOrderRequest) : ResponseEntity<URI> {
        val token = jwtTokenService.getBearerTokenHeader()
        if(token.isNullOrBlank())
            throw ForbiddenException("Invalid token")
        val jwtToken = token.substring(7) // Remove "Bearer " prefix

        // Check if the session exists
        val email = jwtTokenService.extractEmail(jwtToken)
        if(email.isNullOrBlank())
            throw BadRequestException("Bad token provided")
        val id = orderService.createOrder(request, jwtToken)
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("api/order/$id/info").build().toUri()).build();
    }

    @GetMapping("/{id}/info", produces = ["application/json"])
    @Operation(summary = "Get info about the order", security = [SecurityRequirement(name = "bearerAuth")])
    fun getOrderInfo(@PathVariable id: Int) : OrderResponse {
        return orderService.getOrder(id)
    }
}