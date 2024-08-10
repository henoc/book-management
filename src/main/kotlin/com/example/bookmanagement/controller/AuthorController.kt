package com.example.bookmanagement.controller

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.service.AuthorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * 著者に関するリクエストを処理するコントローラークラス
 * 著者のCRUD操作を提供します。
 */
@RestController
@RequestMapping("/api/authors")
class AuthorController(private val authorService: AuthorService) {

    /**
     * 全ての著者を取得します。
     * @return 著者のリスト
     */
    @GetMapping
    fun getAllAuthors(): List<Author> = authorService.getAllAuthors()

    /**
     * 指定されたIDの著者を取得します。
     * @param id 著者ID
     * @return 著者オブジェクト
     */
    @GetMapping("/{id}")
    fun getAuthor(@PathVariable id: Int): Author = authorService.getAuthor(id)

    /**
     * 新しい著者を作成します。
     * @param author 作成する著者オブジェクト
     * @return 作成された著者オブジェクト
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAuthor(@Valid @RequestBody author: Author): Author = authorService.createAuthor(author)

    /**
     * 既存の著者情報を更新します。
     * @param id 更新する著者のID
     * @param author 更新内容を含む著者オブジェクト
     * @return 更新された著者オブジェクト
     */
    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable id: Int, @Valid @RequestBody author: Author): Author {
        if (author.id != id) {
            throw IllegalArgumentException("ID in path and body do not match")
        }
        return authorService.updateAuthor(author)
    }

    /**
     * 指定されたIDの著者を削除します。
     * @param id 削除する著者のID
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteAuthor(@PathVariable id: Int) = authorService.deleteAuthor(id)
}

