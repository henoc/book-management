package com.example.bookmanagement.service

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.repository.AuthorRepository
import org.springframework.stereotype.Service

/**
 * 著者に関するビジネスロジックを提供するサービスクラス
 */
@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    /**
     * 指定されたIDの著者を取得します。
     * @param id 著者ID
     * @return 著者オブジェクト
     * @throws NoSuchElementException 指定されたIDの著者が存在しない場合
     */
    fun getAuthor(id: Int): Author =
        authorRepository.findById(id) ?: throw NoSuchElementException("Author not found with id: $id")

    /**
     * 全ての著者を取得します。
     * @return 著者のリスト
     */
    fun getAllAuthors(): List<Author> = authorRepository.findAll()

    /**
     * 新しい著者を作成します。
     * @param author 作成する著者オブジェクト
     * @return 作成された著者オブジェクト
     */
    fun createAuthor(author: Author): Author = authorRepository.save(author)

    /**
     * 既存の著者情報を更新します。
     * @param author 更新内容を含む著者オブジェクト
     * @return 更新された著者オブジェクト
     */
    fun updateAuthor(author: Author): Author = authorRepository.update(author)

    /**
     * 指定されたIDの著者を削除します。
     * @param id 削除する著者のID
     */
    fun deleteAuthor(id: Int) = authorRepository.delete(id)
}
