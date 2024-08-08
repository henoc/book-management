package com.example.bookmanagement.repository

import com.example.bookmanagement.entity.Book
import org.springframework.stereotype.Repository

@Repository
interface BookRepository {
    fun findById(id: Int): Book?
    fun findAll(): List<Book>
    fun findByAuthorId(authorId: Int): List<Book>
    fun save(book: Book): Book
    fun update(book: Book): Book
    fun delete(id: Int)
}
