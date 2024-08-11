package com.example.bookmanagement.exception

/**
 * 無効な著者IDが指定された場合にスローされる例外
 *
 * @param message エラーメッセージ
 */
class InvalidAuthorException(message: String) : RuntimeException(message)
