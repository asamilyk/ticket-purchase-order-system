package com.example.ticketservice.controller

import com.example.ticketservice.model.response.StationResponse
import com.example.ticketservice.service.StationService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/station")
@Validated
class StationController(
    val stationService: StationService
) {
    @GetMapping("/info", produces = ["application/json"])
    @Operation(summary = "Get list of available stations")
    fun getStations(): List<StationResponse>{
        return stationService.getStations()
    }
}