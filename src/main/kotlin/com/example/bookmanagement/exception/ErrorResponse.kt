package com.example.bookmanagement.exception

data class ErrorResponse(
    val error: String,
    val message: String,
    val details: List<String> = emptyList()
)