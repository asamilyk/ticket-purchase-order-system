package com.example.ticketservice.handler

import com.example.ticketservice.exception.ConflictException
import com.example.ticketservice.exception.ForbiddenException
import com.example.ticketservice.exception.NotFoundException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUserNotFoundException(ex: ConflictException): ResponseEntity<Map<String, String?>> {
        val errors = mapOf("error" to ex.message)
        return ResponseEntity(errors, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserNotFoundException(ex: NotFoundException): ResponseEntity<Map<String, String?>> {
        val errors = mapOf("error" to ex.message)
        return ResponseEntity(errors, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleUserNotFoundException(ex: ForbiddenException): ResponseEntity<Map<String, String?>> {
        val errors = mapOf("error" to ex.message)
        return ResponseEntity(errors, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage ?: "Invalid value"
            errors[fieldName] = errorMessage
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [ExpiredJwtException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleExpiredJwtException(ex: ExpiredJwtException): ResponseEntity<Any> {
        val errors = mapOf("error" to ex.message)
        return ResponseEntity(errors, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(value = [SignatureException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun invalidBearerTokenException(ex: SignatureException): ResponseEntity<Any> {
        val errors = mapOf("error" to ex.message)
        return ResponseEntity(errors, HttpStatus.UNAUTHORIZED)
    }
}
