package com.example.bookmanagement.entity

data class Book(
    val id: Int? = null,
    val title: String,
    val publicationYear: Int?,
    val authorId: Int
)