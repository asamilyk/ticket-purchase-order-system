package com.example.ticketservice.service

import com.example.ticketservice.model.response.StationResponse
import com.example.ticketservice.repository.StationRepository
import org.springframework.stereotype.Service

@Service
class StationService(
    val stationRepository: StationRepository
) {
    fun getStations(): List<StationResponse>{
        val stations = stationRepository.findAll()
        return stations.map {
            StationResponse(it.name)
        }
    }
}