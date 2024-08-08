package com.example.bookmanagement.controller

import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun getAllBooks(): List<Book> = bookService.getAllBooks()

    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Int): Book? = bookService.getBook(id)

    @GetMapping("/author/{authorId}")
    fun getBooksByAuthor(@PathVariable authorId: Int): List<Book> = bookService.getBooksByAuthor(authorId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody book: Book): Book = bookService.createBook(book)

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Int, @RequestBody book: Book): Book {
        if (book.id != id) {
            throw IllegalArgumentException("ID in path and body do not match")
        }
        return bookService.updateBook(book)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: Int) = bookService.deleteBook(id)
}