package com.example.bookmanagement.entity

import java.time.LocalDate

data class Author(
    val id: Int? = null,
    val name: String,
    val birthDate: LocalDate?
)