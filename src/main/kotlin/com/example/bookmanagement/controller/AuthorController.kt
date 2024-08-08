package com.example.bookmanagement.controller

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.service.AuthorService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/authors")
class AuthorController(private val authorService: AuthorService) {

    @GetMapping
    fun getAllAuthors(): List<Author> = authorService.getAllAuthors()

    @GetMapping("/{id}")
    fun getAuthor(@PathVariable id: Int): Author? = authorService.getAuthor(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAuthor(@RequestBody author: Author): Author = authorService.createAuthor(author)

    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable id: Int, @RequestBody author: Author): Author {
        if (author.id != id) {
            throw IllegalArgumentException("ID in path and body do not match")
        }
        return authorService.updateAuthor(author)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAuthor(@PathVariable id: Int) = authorService.deleteAuthor(id)
}

