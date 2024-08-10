package com.example.bookmanagement.repository.impl

import com.example.bookmanagement.entity.Author
import com.example.bookmanagement.jooq.tables.references.AUTHORS
import com.example.bookmanagement.repository.AuthorRepository
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class AuthorRepositoryImpl(private val dsl: DSLContext) : AuthorRepository {
    override fun findById(id: Int): Author? {
        return dsl.selectFrom(AUTHORS)
            .where(AUTHORS.ID.eq(id))
            .fetchOneInto(Author::class.java)
    }

    override fun findAll(): List<Author> {
        return dsl.selectFrom(AUTHORS)
            .fetchInto(Author::class.java)
    }

    override fun save(author: Author): Author {
        val record = dsl.newRecord(AUTHORS).apply {
            name = author.name
            birthDate = author.birthDate
        }
        record.store()
        return author.copy(id = record.id)
    }

    override fun update(author: Author): Author {
        dsl.update(AUTHORS)
            .set(AUTHORS.NAME, author.name)
            .set(AUTHORS.BIRTH_DATE, author.birthDate)
            .where(AUTHORS.ID.eq(author.id))
            .execute()
        return author
    }

    override fun delete(id: Int) {
        dsl.deleteFrom(AUTHORS)
            .where(AUTHORS.ID.eq(id))
            .execute()
    }
}
