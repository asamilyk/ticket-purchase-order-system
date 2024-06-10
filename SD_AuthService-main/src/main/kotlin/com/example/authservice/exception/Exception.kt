package com.example.authservice.exception

class ConflictException(message: String) : RuntimeException(message)

class BadRequestException(message: String) : RuntimeException(message)

class ForbiddenException(message: String) : RuntimeException(message)

class NotFoundException(message: String): RuntimeException(message)
