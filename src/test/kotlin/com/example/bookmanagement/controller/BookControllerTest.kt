import com.example.bookmanagement.BookManagementApplication
import com.example.bookmanagement.controller.BookController
import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.service.BookService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookController::class)
@ContextConfiguration(classes = [BookManagementApplication::class])
class BookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return all books`() {
        val books = listOf(
            Book(1, "Book 1", 2021, 1),
            Book(2, "Book 2", 2022, 2)
        )
        `when`(bookService.getAllBooks()).thenReturn(books)

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("Book 1"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].title").value("Book 2"))
    }

    @Test
    fun `should return book by id`() {
        val book = Book(1, "Book 1", 2021, 1)
        `when`(bookService.getBook(1)).thenReturn(book)

        mockMvc.perform(get("/api/books/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Book 1"))
    }

    @Test
    fun `should return books by author`() {
        val books = listOf(
            Book(1, "Book 1", 2021, 1),
            Book(2, "Book 2", 2022, 1)
        )
        `when`(bookService.getBooksByAuthor(1)).thenReturn(books)

        mockMvc.perform(get("/api/books/author/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("Book 1"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].title").value("Book 2"))
    }

    @Test
    fun `should create a new book`() {
        val newBook = Book(null, "New Book", 2023, 1)
        val savedBook = Book(3, "New Book", 2023, 1)
        `when`(bookService.createBook(newBook)).thenReturn(savedBook)

        mockMvc.perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newBook))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.title").value("New Book"))
    }

    @Test
    fun `should update an existing book`() {
        val updatedBook = Book(1, "Updated Book", 2023, 1)
        `when`(bookService.updateBook(updatedBook)).thenReturn(updatedBook)

        mockMvc.perform(
            put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Updated Book"))
    }

    @Test
    fun `should delete a book`() {
        mockMvc.perform(delete("/api/books/1"))
            .andExpect(status().isNoContent)

        verify(bookService).deleteBook(1)
    }

    @Test
    fun `should return 404 when book not found`() {
        `when`(bookService.getBook(999)).thenThrow(NoSuchElementException("Book not found with id: 999"))

        mockMvc.perform(get("/api/books/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 400 when updating book with mismatched id`() {
        val updatedBook = Book(2, "Updated Book", 2023, 1)

        mockMvc.perform(
            put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook))
        )
            .andExpect(status().isBadRequest)
    }
}
