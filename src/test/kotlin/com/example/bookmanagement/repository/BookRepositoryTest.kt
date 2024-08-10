package com.example.bookmanagement.repository

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.jooq.tables.references.AUTHORS
import com.example.bookmanagement.jooq.tables.references.BOOKS
import com.example.bookmanagement.repository.impl.AuthorRepositoryImpl
import com.example.bookmanagement.repository.impl.BookRepositoryImpl
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDate

@JooqTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BookRepositoryImpl::class, AuthorRepositoryImpl::class)
class BookRepositoryTest {

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:13")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") {
                String.format(
                    "jdbc:tc:postgresql:13:///%s",
                    postgresContainer.databaseName
                )
            }
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
        }
    }

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Autowired
    private lateinit var dsl: DSLContext

    private lateinit var testAuthor1: Author
    private lateinit var testAuthor2: Author

    @BeforeEach
    fun setup() {
        // Clear the tables before each test
        dsl.deleteFrom(BOOKS).execute()
        dsl.deleteFrom(AUTHORS).execute()

        // Create test authors
        testAuthor1 = authorRepository.save(Author(null, "Test Author 1", LocalDate.of(1980, 1, 1)))
        testAuthor2 = authorRepository.save(Author(null, "Test Author 2", LocalDate.of(1985, 1, 1)))
    }

    @Test
    fun `should save and retrieve book`() {
        val book = Book(null, "Test Book", 2021, testAuthor1.id!!)
        val savedBook = bookRepository.save(book)

        assertNotNull(savedBook.id)
        assertEquals(book.title, savedBook.title)

        val retrievedBook = bookRepository.findById(savedBook.id!!)
        assertNotNull(retrievedBook)
        assertEquals(savedBook, retrievedBook)
    }

    @Test
    fun `should update book`() {
        val book = Book(null, "Original Title", 2021, testAuthor1.id!!)
        val savedBook = bookRepository.save(book)

        val updatedBook = savedBook.copy(title = "Updated Title")
        bookRepository.update(updatedBook)

        val retrievedBook = bookRepository.findById(savedBook.id!!)
        assertNotNull(retrievedBook)
        assertEquals("Updated Title", retrievedBook?.title)
    }

    @Test
    fun `should delete book`() {
        val book = Book(null, "Book to Delete", 2021, testAuthor1.id!!)
        val savedBook = bookRepository.save(book)

        bookRepository.delete(savedBook.id!!)

        val retrievedBook = bookRepository.findById(savedBook.id!!)
        assertNull(retrievedBook)
    }

    @Test
    fun `should find all books`() {
        val book1 = Book(null, "Book 1", 2021, testAuthor1.id!!)
        val book2 = Book(null, "Book 2", 2022, testAuthor2.id!!)
        bookRepository.save(book1)
        bookRepository.save(book2)

        val allBooks = bookRepository.findAll()
        assertEquals(2, allBooks.size)
    }

    @Test
    fun `should find books by author id`() {
        val book1 = Book(null, "Book 1", 2021, testAuthor1.id!!)
        val book2 = Book(null, "Book 2", 2022, testAuthor1.id!!)
        val book3 = Book(null, "Book 3", 2023, testAuthor2.id!!)
        bookRepository.save(book1)
        bookRepository.save(book2)
        bookRepository.save(book3)

        val authorBooks = bookRepository.findByAuthorId(testAuthor1.id!!)
        assertEquals(2, authorBooks.size)
        assertTrue(authorBooks.all { it.authorId == testAuthor1.id })
    }
}
