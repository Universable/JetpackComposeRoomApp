package com.example.stamen_stoyanov_android.data.repositores

import com.example.stamen_stoyanov_android.data.dao.AuthorDao
import com.example.stamen_stoyanov_android.data.entities.Author
import kotlinx.coroutines.flow.Flow

class AuthorRepositoryImp(private val authorDao: AuthorDao) : AuthorRepository {

    override fun update(author: Author) = authorDao.updateAuthor(author)

    override fun delete(author: Author) = authorDao.deleteAuthor(author)

    override fun getById(id: Int): Author = authorDao.getAuthorById(id)

    override fun getAll(): List<Author> = authorDao.getallAuthors()

    override fun getaAllAuthorsFlow(): Flow<List<Author>> = authorDao.getaAllAuthorsFlow()
}