package com.example.bookmanagement.exception

/**
 * エラーレスポンスを表すデータクラス
 *
 * @property error エラーの種類
 * @property message エラーの詳細メッセージ
 * @property details エラーの追加詳細情報のリスト（オプション）
 */
data class ErrorResponse(
    val error: String,
    val message: String,
    val details: List<String> = emptyList()
)
