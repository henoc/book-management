package com.example.bookmanagement.entity

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Past
import jakarta.validation.constraints.Size
import java.time.LocalDate

/**
 * 著者エンティティクラス
 * 著者の情報を表現します。
 *
 * @property id 著者ID（自動生成）
 * @property name 著者名
 * @property birthDate 生年月日
 */
data class Author(
    val id: Int? = null,

    @field:NotBlank(message = "名前は空白にできません")
    @field:Size(min = 2, max = 100, message = "名前は2文字以上100文字以下である必要があります")
    val name: String,

    @field:Past(message = "生年月日は過去の日付である必要があります")
    val birthDate: LocalDate?
)
