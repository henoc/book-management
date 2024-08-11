package com.example.bookmanagement.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

/**
 * グローバル例外ハンドラクラス
 * アプリケーション全体で発生する例外を捕捉し、適切なレスポンスを返します。
 */
@ControllerAdvice
class GlobalExceptionHandler {

    /**
     * バリデーション例外をハンドリングします。
     * @param ex バリデーション例外
     * @param request Webリクエスト
     * @return エラーレスポンス
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.allErrors.mapNotNull { it.defaultMessage }
        val errorResponse = ErrorResponse(
            error = "Validation Failed",
            message = "The following validation errors occurred",
            details = errors
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    /**
     * 一般的な例外をハンドリングします。
     * @param ex 例外
     * @return エラーレスポンス
     */
    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = "Internal Server Error",
            message = "An unexpected error occurred"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    /**
     * 不正な引数例外をハンドリングします。
     * @param ex 不正な引数例外
     * @return エラーレスポンス
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = "Bad Request",
            message = ex.message ?: "Invalid argument provided"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /**
     * 要素が見つからない例外をハンドリングします。
     * @param ex 要素が見つからない例外
     * @return エラーレスポンス
     */
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(ex: NoSuchElementException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = "Not Found",
            message = ex.message ?: "The requested element could not be found"
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    /**
     * 著者が存在しない例外をハンドリングします。
     * @param ex 著者が存在しない例外
     * @return エラーレスポンス
     */
    @ExceptionHandler(InvalidAuthorException::class)
    fun handleInvalidAuthorException(ex: InvalidAuthorException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            error = "Bad Request",
            message = ex.message ?: "Invalid author provided"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}

