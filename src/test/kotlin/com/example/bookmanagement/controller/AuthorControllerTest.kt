package com.example.bookmanagement.controller

import com.example.bookmanagement.BookManagementApplication
import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.service.AuthorService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@WebMvcTest(AuthorController::class)
@ContextConfiguration(classes = [BookManagementApplication::class])
class AuthorControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authorService: AuthorService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return all authors`() {
        val authors = listOf(
            Author(1, "Author 1", LocalDate.of(1980, 1, 1)),
            Author(2, "Author 2", LocalDate.of(1985, 1, 1))
        )
        `when`(authorService.getAllAuthors()).thenReturn(authors)

        mockMvc.perform(get("/api/authors"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Author 1"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].name").value("Author 2"))
    }

    @Test
    fun `should return author by id`() {
        val author = Author(1, "Author 1", LocalDate.of(1980, 1, 1))
        `when`(authorService.getAuthor(1)).thenReturn(author)

        mockMvc.perform(get("/api/authors/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Author 1"))
    }

    @Test
    fun `should return 404 when author not found`() {
        `when`(authorService.getAuthor(999)).thenThrow(NoSuchElementException("Author not found with id: 999"))

        mockMvc.perform(get("/api/authors/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should create a new author`() {
        val newAuthor = Author(null, "New Author", LocalDate.of(1990, 1, 1))
        val savedAuthor = Author(3, "New Author", LocalDate.of(1990, 1, 1))
        `when`(authorService.createAuthor(newAuthor)).thenReturn(savedAuthor)

        mockMvc.perform(post("/api/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newAuthor)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("New Author"))
    }

    @Test
    fun `should return 400 when creating author with invalid data`() {
        val invalidAuthor = Author(null, "", LocalDate.of(2025, 1, 1))

        mockMvc.perform(post("/api/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidAuthor)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should update an existing author`() {
        val updatedAuthor = Author(1, "Updated Author", LocalDate.of(1985, 1, 1))
        `when`(authorService.updateAuthor(updatedAuthor)).thenReturn(updatedAuthor)

        mockMvc.perform(put("/api/authors/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedAuthor)))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Updated Author"))
    }

    @Test
    fun `should return 400 when updating author with mismatched id`() {
        val updatedAuthor = Author(2, "Updated Author", LocalDate.of(1985, 1, 1))

        mockMvc.perform(put("/api/authors/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedAuthor)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should delete an author`() {
        mockMvc.perform(delete("/api/authors/1"))
            .andExpect(status().isNoContent)

        verify(authorService).deleteAuthor(1)
    }
}