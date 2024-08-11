package com.example.bookmanagement.repository.impl

import com.example.bookmanagement.entity.Book
import com.example.bookmanagement.jooq.tables.references.BOOKS
import com.example.bookmanagement.repository.BookRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

/**
 * 書籍リポジトリの実装クラス
 * jOOQを使用してデータベース操作を行います。
 */
@Repository
class BookRepositoryImpl(private val dsl: DSLContext) : BookRepository {

    override fun findById(id: Int): Book? {
        return dsl.selectFrom(BOOKS)
            .where(BOOKS.ID.eq(id))
            .fetchOneInto(Book::class.java)
    }

    override fun findAll(): List<Book> {
        return dsl.selectFrom(BOOKS)
            .fetchInto(Book::class.java)
    }

    override fun findByAuthorId(authorId: Int): List<Book> {
        return dsl.selectFrom(BOOKS)
            .where(BOOKS.AUTHOR_ID.eq(authorId))
            .fetchInto(Book::class.java)
    }

    override fun save(book: Book): Book {
        val record = dsl.newRecord(BOOKS).apply {
            title = book.title
            publicationYear = book.publicationYear
            authorId = book.authorId
        }
        record.store()
        return book.copy(id = record.id)
    }

    override fun update(book: Book): Book {
        dsl.update(BOOKS)
            .set(BOOKS.TITLE, book.title)
            .set(BOOKS.PUBLICATION_YEAR, book.publicationYear)
            .set(BOOKS.AUTHOR_ID, book.authorId)
            .where(BOOKS.ID.eq(book.id))
            .execute()
        return book
    }
}
