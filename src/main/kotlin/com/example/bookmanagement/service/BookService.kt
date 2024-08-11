package com.example.bookmanagement.service

import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.exception.InvalidAuthorException
import com.example.bookmanagement.repository.AuthorRepository
import com.example.bookmanagement.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 書籍に関するビジネスロジックを提供するサービスクラス
 */
@Service
class BookService(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
) {

    /**
     * 指定されたIDの書籍を取得します。
     * @param id 書籍ID
     * @return 書籍オブジェクト
     * @throws NoSuchElementException 指定されたIDの書籍が存在しない場合
     */
    fun getBook(id: Int): Book =
        bookRepository.findById(id) ?: throw NoSuchElementException("Book not found with id: $id")

    /**
     * 全ての書籍を取得します。
     * @return 書籍のリスト
     */
    fun getAllBooks(): List<Book> = bookRepository.findAll()

    /**
     * 指定された著者IDに紐づく全ての書籍を取得します。
     * @param authorId 著者ID
     * @return 書籍のリスト
     */
    fun getBooksByAuthor(authorId: Int): List<Book> = bookRepository.findByAuthorId(authorId)

    /**
     * 新しい書籍を作成します。
     * @param book 作成する書籍オブジェクト
     * @return 作成された書籍オブジェクト
     * @throws InvalidAuthorException 指定された著者IDが存在しない場合
     */
    @Transactional
    fun createBook(book: Book): Book {
        authorRepository.findById(book.authorId)
            ?: throw InvalidAuthorException("Author with id ${book.authorId} does not exist")
        return bookRepository.save(book)
    }

    /**
     * 既存の書籍情報を更新します。
     * @param book 更新内容を含む書籍オブジェクト
     * @return 更新された書籍オブジェクト
     * @throws InvalidAuthorException 指定された著者IDの著者が存在しない場合
     */
    @Transactional
    fun updateBook(book: Book): Book {
        authorRepository.findById(book.authorId)
            ?: throw InvalidAuthorException("Author with id ${book.authorId} does not exist")
        return bookRepository.update(book)
    }
}
