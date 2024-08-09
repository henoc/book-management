package com.example.bookmanagement.service

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.repository.AuthorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

class AuthorServiceTest {

    private lateinit var authorService: AuthorService
    private lateinit var authorRepository: AuthorRepository

    @BeforeEach
    fun setup() {
        authorRepository = mock(AuthorRepository::class.java)
        authorService = AuthorService(authorRepository)
    }

    @Test
    fun `getAuthor should return author when it exists`() {
        val author = Author(1, "Test Author", LocalDate.of(1980, 1, 1))
        `when`(authorRepository.findById(1)).thenReturn(author)

        val result = authorService.getAuthor(1)

        assertEquals(author, result)
        verify(authorRepository).findById(1)
    }

    @Test
    fun `getAuthor should throw NoSuchElementException when author doesn't exist`() {
        `when`(authorRepository.findById(1)).thenReturn(null)

        assertThrows<NoSuchElementException> {
            authorService.getAuthor(1)
        }
        verify(authorRepository).findById(1)
    }

    @Test
    fun `getAllAuthors should return all authors`() {
        val authors = listOf(
            Author(1, "Author 1", LocalDate.of(1980, 1, 1)),
            Author(2, "Author 2", LocalDate.of(1985, 1, 1))
        )
        `when`(authorRepository.findAll()).thenReturn(authors)

        val result = authorService.getAllAuthors()

        assertEquals(authors, result)
        verify(authorRepository).findAll()
    }

    @Test
    fun `createAuthor should save and return the new author`() {
        val newAuthor = Author(null, "New Author", LocalDate.of(1990, 1, 1))
        val savedAuthor = Author(3, "New Author", LocalDate.of(1990, 1, 1))
        `when`(authorRepository.save(newAuthor)).thenReturn(savedAuthor)

        val result = authorService.createAuthor(newAuthor)

        assertEquals(savedAuthor, result)
        verify(authorRepository).save(newAuthor)
    }

    @Test
    fun `updateAuthor should update and return the author`() {
        val updatedAuthor = Author(1, "Updated Author", LocalDate.of(1985, 1, 1))
        `when`(authorRepository.update(updatedAuthor)).thenReturn(updatedAuthor)

        val result = authorService.updateAuthor(updatedAuthor)

        assertEquals(updatedAuthor, result)
        verify(authorRepository).update(updatedAuthor)
    }

    @Test
    fun `deleteAuthor should call repository's delete method`() {
        authorService.deleteAuthor(1)

        verify(authorRepository).delete(1)
    }

    @Test
    fun `updateAuthor should throw NoSuchElementException when author doesn't exist`() {
        val nonExistentAuthor = Author(999, "Non-existent Author", LocalDate.of(1990, 1, 1))
        `when`(authorRepository.update(nonExistentAuthor)).thenThrow(NoSuchElementException("Author not found with id: 999"))

        assertThrows<NoSuchElementException> {
            authorService.updateAuthor(nonExistentAuthor)
        }
        verify(authorRepository).update(nonExistentAuthor)
    }
}
