package com.example.ticketservice.scheduling

import com.example.ticketservice.service.OrderService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledTasks(
    private val orderService: OrderService
) {
    @Scheduled(fixedRate = 30000) // make job every 30 seconds
    fun processOrders() {
        orderService.processOrders()
    }
}
