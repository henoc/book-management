package com.example.bookmanagement.controller

import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.service.BookService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 書籍に関するリクエストを処理するコントローラークラス
 * 書籍のCRUD操作および著者に紐づく書籍の取得機能を提供します。
 */
@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    /**
     * 全ての書籍を取得します。
     * @return 書籍のリスト
     */
    @GetMapping
    fun getAllBooks(): List<Book> = bookService.getAllBooks()

    /**
     * 指定されたIDの書籍を取得します。
     * @param id 書籍ID
     * @return 書籍オブジェクト
     */
    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Int): Book = bookService.getBook(id)

    /**
     * 指定された著者IDに紐づく全ての書籍を取得します。
     * @param authorId 著者ID
     * @return 書籍のリスト
     */
    @GetMapping("/author/{authorId}")
    fun getBooksByAuthor(@PathVariable authorId: Int): List<Book> = bookService.getBooksByAuthor(authorId)

    /**
     * 新しい書籍を作成します。
     * @param book 作成する書籍オブジェクト
     * @return 作成された書籍オブジェクト
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@Valid @RequestBody book: Book): Book = bookService.createBook(book)

    /**
     * 既存の書籍情報を更新します。
     * @param id 更新する書籍のID
     * @param book 更新内容を含む書籍オブジェクト
     * @return 更新された書籍オブジェクト
     */
    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Int, @Valid @RequestBody book: Book): ResponseEntity<Any> {
        if (book.id != id) {
            throw IllegalArgumentException("ID in path ($id) does not match ID in body (${book.id})")
        }

        val updatedBook = bookService.updateBook(book)
        return ResponseEntity.ok(updatedBook)
    }
}
