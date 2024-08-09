package com.example.bookmanagement.entity

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class Author(
    val id: Int? = null,

    @field:NotBlank(message = "Name cannot be blank")
    @field:Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    val name: String,

    @field:Past(message = "Birth date must be in the past")
    val birthDate: LocalDate?
)