package com.example.authservice.model.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "session")
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "token", nullable = false)
    val token: String,

    @Column(name = "expires", nullable = false)
    val expires: Date
)
