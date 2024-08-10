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
    val id: Int? = null,

    @field:NotBlank(message = "Title cannot be blank")
    val title: String,

    @field:Min(value = 0, message = "Publication year must be non-negative")
    val publicationYear: Int?,

    @field:NotNull(message = "Author ID is required")
    @field:Min(value = 1, message = "Author ID must be positive")
    val authorId: Int
)
