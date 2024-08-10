package com.example.bookmanagement.service

import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {
    fun getBook(id: Int): Book =
        bookRepository.findById(id) ?: throw NoSuchElementException("Book not found with id: $id")

    fun getAllBooks(): List<Book> = bookRepository.findAll()
    fun getBooksByAuthor(authorId: Int): List<Book> = bookRepository.findByAuthorId(authorId)
    fun createBook(book: Book): Book = bookRepository.save(book)
    fun updateBook(book: Book): Book = bookRepository.update(book)
    fun deleteBook(id: Int) = bookRepository.delete(id)
}
