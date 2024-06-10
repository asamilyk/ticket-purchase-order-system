package com.example.ticketservice.model.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "\"order\"")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "from_station_id", nullable = false)
    val departure: Station,

    @ManyToOne
    @JoinColumn(name = "to_station_id", nullable = false)
    val arrival: Station,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "created")
    val created: Date,


    @Column(name = "status")
    var status: Int,
)
