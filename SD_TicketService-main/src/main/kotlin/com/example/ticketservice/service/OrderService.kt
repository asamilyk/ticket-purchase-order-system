package com.example.ticketservice.service

import com.example.ticketservice.exception.BadRequestException
import com.example.ticketservice.exception.ForbiddenException
import com.example.ticketservice.exception.NotFoundException
import com.example.ticketservice.model.domain.Order
import com.example.ticketservice.model.enums.OrderStatus
import com.example.ticketservice.model.request.CreateOrderRequest
import com.example.ticketservice.model.response.OrderResponse
import com.example.ticketservice.repository.OrderRepository
import com.example.ticketservice.repository.StationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*
import kotlin.random.Random

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val stationRepository: StationRepository,
    private val sessionService: SessionService,
    private val tokenService: TokenService
) {
    fun createOrder(request: CreateOrderRequest, token: String): Int{
        val depart = stationRepository.findByName(request.from)
            ?: throw NotFoundException("No station with name: ${request.from}. Check /api/station/info to find available stations.")
        val arrival = stationRepository.findByName(request.to)
            ?: throw NotFoundException("No station with name ${request.to}. Check /api/station/info to find available stations.")
        val id = tokenService.extractId(token)
            ?: throw ForbiddenException("Invalid token")
        val order = Order(
            departure = depart,
            arrival = arrival,
            userId = id,
            created = Date(),
            status = OrderStatus.CHECK.status
        )
        orderRepository.save(order)
        return order.id
    }

    fun getOrder(id: Int): OrderResponse {
        val order = orderRepository.findById(id)
        if(order.isEmpty)
            throw NotFoundException("No order with id: $id")
        val orderEntity = order.get()
        val departure = orderEntity.departure.name
        val arrival = orderEntity.arrival.name
        val status = OrderStatus.getByStatus(orderEntity.status)
            ?: throw IllegalArgumentException("Found unknown status: ${orderEntity.status}")
        return OrderResponse(
            id = orderEntity.id,
            userId = orderEntity.userId,
            from = departure,
            to = arrival,
            status = status,
            createdAt = orderEntity.created
        )
    }

    @Transactional
    fun processOrders(){
        val orders = orderRepository.findAllByStatus(OrderStatus.CHECK.status)
        orders.forEach { order ->
            order.status = if (Random.nextBoolean()) OrderStatus.SUCCESS.status
                            else OrderStatus.REJECTION.status
            orderRepository.save(order)
        }
    }
}