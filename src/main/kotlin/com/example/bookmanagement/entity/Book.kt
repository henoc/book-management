package com.example.bookmanagement.entity

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

/**
 * 書籍エンティティクラス
 * 書籍の情報を表現します。
 *
 * @property id 書籍ID（自動生成）
 * @property title 書籍タイトル
 * @property publicationYear 出版年
 * @property authorId 著者ID（外部キー）
 */
data class Book(
    val id: Int?,
    @field:NotBlank(message = "タイトルは空白にできません")
    val title: String,
    @field:Min(value = 0, message = "出版年は0以上である必要があります")
    val publicationYear: Int?,
    @field:NotNull(message = "著者IDは必須です")
    @field:Min(value = 1, message = "著者IDは1以上である必要があります")
    val authorId: Int
)
