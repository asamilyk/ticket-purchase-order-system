package com.example.authservice.repository

import org.springframework.stereotype.Repository
import com.example.authservice.model.domain.User

import java.util.*

//@Repository
//class UserRepository(private val encoder: PasswordEncoder) {
//
//    private val users = mutableSetOf(
//        User(
//            id = UUID.randomUUID(),
//            userName = "user1",
//            email = "user1@gmail.com",
//            password = encoder.encode("pass1"),
//        ),
//        User(
//            id = UUID.randomUUID(),
//            userName = "user2",
//            email = "admin1@gmail.com",
//            password = encoder.encode("pass2"),
//        ),
//        User(
//            id = UUID.randomUUID(),
//            userName = "user3",
//            email = "user2@gmail.com",
//            password = encoder.encode("pass3"),
//        ),
//    )
//
//    fun save(user: User): Boolean {
//        val updated = user.copy(password = encoder.encode(user.password))
//        println("User saved")
//        return users.add(updated)
//    }
//
//    fun findByEmail(email: String): User? =
//        users.firstOrNull { it.email == email }
//
//    fun findAll(): Set<User> = users
//
//    fun findByUUID(uuid: UUID): User? =
//        users.firstOrNull { it.id == uuid }
//
//    fun deleteByUUID(uuid: UUID): Boolean {
//        val foundUser = findByUUID(uuid)
//
//        return foundUser?.let {
//            users.removeIf {
//                it.id == uuid
//            }
//        } ?: false
//    }
//}


import org.springframework.data.jpa.repository.JpaRepository

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
}
