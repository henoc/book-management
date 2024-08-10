package com.example.bookmanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * 書籍管理システムのメインアプリケーションクラス
 * このクラスはSpring Bootアプリケーションのエントリーポイントとなります。
 */
@SpringBootApplication
class BookManagementApplication

fun main(args: Array<String>) {
    runApplication<BookManagementApplication>(*args)
}
