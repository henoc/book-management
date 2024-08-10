package com.example.bookmanagement.service

import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.repository.BookRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

class BookServiceTest {

    private lateinit var bookService: BookService
    private lateinit var bookRepository: BookRepository

    @BeforeEach
    fun setup() {
        bookRepository = mock(BookRepository::class.java)
        bookService = BookService(bookRepository)
    }

    @Test
    fun `getBook should return book when it exists`() {
        val book = Book(1, "Test Book", 2023, 1)
        `when`(bookRepository.findById(1)).thenReturn(book)

        val result = bookService.getBook(1)

        assertEquals(book, result)
        verify(bookRepository).findById(1)
    }

    @Test
    fun `getBook should throw NoSuchElementException when book doesn't exist`() {
        `when`(bookRepository.findById(1)).thenReturn(null)

        assertThrows<NoSuchElementException> {
            bookService.getBook(1)
        }
        verify(bookRepository).findById(1)
    }

    @Test
    fun `getAllBooks should return all books`() {
        val books = listOf(
            Book(1, "Book 1", 2021, 1),
            Book(2, "Book 2", 2022, 2)
        )
        `when`(bookRepository.findAll()).thenReturn(books)

        val result = bookService.getAllBooks()

        assertEquals(books, result)
        verify(bookRepository).findAll()
    }

    @Test
    fun `getBooksByAuthor should return books by author`() {
        val books = listOf(
            Book(1, "Book 1", 2021, 1),
            Book(2, "Book 2", 2022, 1)
        )
        `when`(bookRepository.findByAuthorId(1)).thenReturn(books)

        val result = bookService.getBooksByAuthor(1)

        assertEquals(books, result)
        verify(bookRepository).findByAuthorId(1)
    }

    @Test
    fun `createBook should save and return the new book`() {
        val newBook = Book(null, "New Book", 2023, 1)
        val savedBook = Book(3, "New Book", 2023, 1)
        `when`(bookRepository.save(newBook)).thenReturn(savedBook)

        val result = bookService.createBook(newBook)

        assertEquals(savedBook, result)
        verify(bookRepository).save(newBook)
    }

    @Test
    fun `updateBook should update and return the book`() {
        val updatedBook = Book(1, "Updated Book", 2023, 1)
        `when`(bookRepository.update(updatedBook)).thenReturn(updatedBook)

        val result = bookService.updateBook(updatedBook)

        assertEquals(updatedBook, result)
        verify(bookRepository).update(updatedBook)
    }

    @Test
    fun `deleteBook should call repository's delete method`() {
        bookService.deleteBook(1)

        verify(bookRepository).delete(1)
    }
}
