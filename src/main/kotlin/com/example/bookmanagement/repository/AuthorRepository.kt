package com.example.bookmanagement.repository

import com.example.bookmanagement.entity.Author
import org.springframework.stereotype.Repository

/**
 * 著者リポジトリのインターフェース
 * 著者データのデータベース操作を定義します。
 */
@Repository
interface AuthorRepository {

    /**
     * 指定されたIDの著者を検索します。
     * @param id 著者ID
     * @return 見つかった著者、または存在しない場合はnull
     */
    fun findById(id: Int): Author?

    /**
     * すべての著者を取得します。
     * @return 著者のリスト
     */
    fun findAll(): List<Author>

    /**
     * 新しい著者を保存します。
     * @param author 保存する著者オブジェクト
     * @return 保存された著者（IDが設定されます）
     */
    fun save(author: Author): Author

    /**
     * 既存の著者情報を更新します。
     * @param author 更新する著者オブジェクト
     * @return 更新された著者
     */
    fun update(author: Author): Author

    /**
     * 指定されたIDの著者を削除します。
     * @param id 削除する著者のID
     */
    fun delete(id: Int)
}
