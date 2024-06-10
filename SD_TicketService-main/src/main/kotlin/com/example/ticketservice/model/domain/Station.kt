package com.example.ticketservice.model.domain

import jakarta.persistence.*

@Entity
@Table(name = "station")
data class Station(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "station")
    val name: String
)