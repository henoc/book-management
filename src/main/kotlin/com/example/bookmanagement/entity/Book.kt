package com.example.bookmanagement.entity

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class Book(
    val id: Int?,
    @field:NotBlank(message = "Title cannot be blank")
    val title: String,
    @field:Min(value = 0, message = "Publication year must be non-negative")
    val publicationYear: Int?,
    @field:NotNull(message = "Author ID is required")
    @field:Min(value = 1, message = "Author ID must be positive")
    val authorId: Int
)