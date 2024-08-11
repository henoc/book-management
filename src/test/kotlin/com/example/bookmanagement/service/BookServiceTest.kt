package com.example.bookmanagement.service

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.exception.InvalidAuthorException
import com.example.bookmanagement.repository.AuthorRepository
import com.example.bookmanagement.repository.BookRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import java.time.LocalDate

class BookServiceTest {

    private lateinit var bookService: BookService
    private lateinit var bookRepository: BookRepository
    private lateinit var authorRepository: AuthorRepository

    @BeforeEach
    fun setup() {
        bookRepository = mock(BookRepository::class.java)
        authorRepository = mock(AuthorRepository::class.java)
        bookService = BookService(bookRepository, authorRepository)
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
    fun `createBook should save book when author exists`() {
        val authorId = 1
        val newBook = Book(null, "New Book", 2023, authorId)
        val savedBook = Book(1, "New Book", 2023, authorId)
        val author = Author(authorId, "Existing Author", LocalDate.of(1980, 1, 1))

        `when`(authorRepository.findById(authorId)).thenReturn(author)
        `when`(bookRepository.save(newBook)).thenReturn(savedBook)

        val result = bookService.createBook(newBook)

        assertEquals(savedBook, result)
        verify(authorRepository).findById(authorId)
        verify(bookRepository).save(newBook)
    }

    @Test
    fun `createBook should throw InvalidAuthorException when author doesn't exist`() {
        val newBook = Book(null, "New Book", 2023, 999)
        `when`(authorRepository.findById(999)).thenReturn(null)

        assertThrows<InvalidAuthorException> {
            bookService.createBook(newBook)
        }
        verify(authorRepository).findById(999)
        verify(bookRepository, never()).save(any())
    }

    @Test
    fun `updateBook should update book when author exists`() {
        val authorId = 1
        val updatedBook = Book(1, "Updated Book", 2023, authorId)
        val author = Author(authorId, "Existing Author", LocalDate.of(1980, 1, 1))

        `when`(authorRepository.findById(authorId)).thenReturn(author)
        `when`(bookRepository.update(updatedBook)).thenReturn(updatedBook)

        val result = bookService.updateBook(updatedBook)

        assertEquals(updatedBook, result)
        verify(authorRepository).findById(authorId)
        verify(bookRepository).update(updatedBook)
    }

    @Test
    fun `updateBook should throw InvalidAuthorException when author doesn't exist`() {
        val updatedBook = Book(1, "Updated Book", 2023, 999)
        `when`(authorRepository.findById(999)).thenReturn(null)

        assertThrows<InvalidAuthorException> {
            bookService.updateBook(updatedBook)
        }
        verify(authorRepository).findById(999)
        verify(bookRepository, never()).update(any())
    }
}
