package com.example.stamen_stoyanov_android.data.repositores

import com.example.stamen_stoyanov_android.data.dao.AuthorBookDao
import com.example.stamen_stoyanov_android.data.dao.AuthorDao
import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.entities.AuthorBook
import com.example.stamen_stoyanov_android.data.entities.AuthorToBook
import com.example.stamen_stoyanov_android.data.entities.Book
import com.example.stamen_stoyanov_android.data.entities.BookAuthor
import kotlinx.coroutines.flow.Flow

class AuthorToBookRepositoryImp(private val authorBookDao: AuthorBookDao) : AuthorToBookRepository {

    override fun insertBook(book: Book): Long = authorBookDao.insertBook(book)

    override fun insertAuthor(author: Author): Long = authorBookDao.insertAuthor(author)

    override fun insertAuthorToBook(authorToBook: AuthorToBook): Long = authorBookDao.insertAuthorToBook(authorToBook)

    override fun getAuthorBook(authorId: Long): Flow<AuthorBook> = authorBookDao.getAuthorBook(authorId)

    override fun getBookAuthor(bookId: Long): Flow<BookAuthor> = authorBookDao.getBookAuthor(bookId)

    override fun updateAuthorToBook(authorToBook: AuthorToBook) = authorBookDao.updateAuthorToBook(authorToBook)

    override fun deleteAuthorToBook(authorToBook: AuthorToBook) = authorBookDao.deleteAuthorToBook(authorToBook)

    override fun getAuthorToBook(authorId: Long, bookId: Long): AuthorToBook = authorBookDao.getAuthorToBook(authorId,bookId)

    //override fun getAllAuthorsStream() = authorBookDao.getAllAuthors()

}
