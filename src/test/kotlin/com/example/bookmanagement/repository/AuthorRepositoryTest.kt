package com.example.bookmanagement.repository

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.jooq.tables.references.AUTHORS
import com.example.bookmanagement.repository.impl.AuthorRepositoryImpl
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
@Import(AuthorRepositoryImpl::class)
class AuthorRepositoryTest {

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:13")

        @JvmStatic
        @DynamicPropertySource
        @Suppress("unused")
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
    private lateinit var authorRepository: AuthorRepository

    @Autowired
    private lateinit var dsl: DSLContext

    @BeforeEach
    fun setup() {
        // Clear the table before each test
        dsl.deleteFrom(AUTHORS).execute()
    }

    @Test
    fun `should save and retrieve author`() {
        val author = Author(null, "John Doe", LocalDate.of(1980, 1, 1))
        val savedAuthor = authorRepository.save(author)

        assertNotNull(savedAuthor.id)
        assertEquals(author.name, savedAuthor.name)

        val retrievedAuthor = authorRepository.findById(savedAuthor.id!!)
        assertNotNull(retrievedAuthor)
        assertEquals(savedAuthor, retrievedAuthor)
    }

    @Test
    fun `should update author`() {
        val author = Author(null, "Original Name", LocalDate.of(1980, 1, 1))
        val savedAuthor = authorRepository.save(author)

        val updatedAuthor = savedAuthor.copy(name = "Updated Name")
        authorRepository.update(updatedAuthor)

        val retrievedAuthor = authorRepository.findById(savedAuthor.id!!)
        assertNotNull(retrievedAuthor)
        assertEquals("Updated Name", retrievedAuthor?.name)
    }

    @Test
    fun `should delete author`() {
        val author = Author(null, "Author to Delete", LocalDate.of(1980, 1, 1))
        val savedAuthor = authorRepository.save(author)

        authorRepository.delete(savedAuthor.id!!)

        val retrievedAuthor = authorRepository.findById(savedAuthor.id!!)
        assertNull(retrievedAuthor)
    }

    @Test
    fun `should find all authors`() {
        val author1 = Author(null, "Author 1", LocalDate.of(1980, 1, 1))
        val author2 = Author(null, "Author 2", LocalDate.of(1985, 1, 1))
        authorRepository.save(author1)
        authorRepository.save(author2)

        val allAuthors = authorRepository.findAll()
        assertEquals(2, allAuthors.size)
    }
}
