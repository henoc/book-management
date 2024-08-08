package com.example.bookmanagement.repository

import com.example.bookmanagement.entity.Author
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository {
    fun findById(id: Int): Author?
    fun findAll(): List<Author>
    fun save(author: Author): Author
    fun update(author: Author): Author
    fun delete(id: Int)
}