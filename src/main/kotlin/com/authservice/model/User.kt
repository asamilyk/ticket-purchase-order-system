package com.authservice.model
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "user")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(nullable = false, unique = true)
    val nickname: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    val created: Timestamp = Timestamp(System.currentTimeMillis())
)
