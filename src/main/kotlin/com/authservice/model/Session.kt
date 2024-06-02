package com.authservice.model
import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "session")
data class Session(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,
    @Column(nullable = false)
    val token: String,
    @Column(nullable = false)
    val expires: Timestamp
)
