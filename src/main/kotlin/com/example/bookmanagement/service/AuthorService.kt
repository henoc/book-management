package com.example.bookmanagement.service

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.repository.AuthorRepository
import org.springframework.stereotype.Service

@Service
class AuthorService(private val authorRepository: AuthorRepository) {
    fun getAuthor(id: Int): Author? = authorRepository.findById(id)
    fun getAllAuthors(): List<Author> = authorRepository.findAll()
    fun createAuthor(author: Author): Author = authorRepository.save(author)
    fun updateAuthor(author: Author): Author = authorRepository.update(author)
    fun deleteAuthor(id: Int) = authorRepository.delete(id)
}