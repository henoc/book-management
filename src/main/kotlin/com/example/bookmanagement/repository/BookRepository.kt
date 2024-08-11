package com.example.bookmanagement.repository

import com.example.bookmanagement.entity.Book
import org.springframework.stereotype.Repository

/**
 * 書籍リポジトリのインターフェース
 * 書籍データのデータベース操作を定義します。
 */
@Repository
interface BookRepository {

    /**
     * 指定されたIDの書籍を検索します。
     * @param id 書籍ID
     * @return 見つかった書籍、または存在しない場合はnull
     */
    fun findById(id: Int): Book?

    /**
     * すべての書籍を取得します。
     * @return 書籍のリスト
     */
    fun findAll(): List<Book>

    /**
     * 指定された著者IDに関連する全ての書籍を検索します。
     * @param authorId 著者ID
     * @return 該当する書籍のリスト
     */
    fun findByAuthorId(authorId: Int): List<Book>

    /**
     * 新しい書籍を保存します。
     * @param book 保存する書籍オブジェクト
     * @return 保存された書籍（IDが設定されます）
     */
    fun save(book: Book): Book

    /**
     * 既存の書籍情報を更新します。
     * @param book 更新する書籍オブジェクト
     * @return 更新された書籍
     */
    fun update(book: Book): Book
}
