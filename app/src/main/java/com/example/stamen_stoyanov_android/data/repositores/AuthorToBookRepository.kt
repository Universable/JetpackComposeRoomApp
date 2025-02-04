package com.example.stamen_stoyanov_android.data.repositores

import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.entities.AuthorBook
import com.example.stamen_stoyanov_android.data.entities.AuthorToBook
import com.example.stamen_stoyanov_android.data.entities.Book
import com.example.stamen_stoyanov_android.data.entities.BookAuthor
import kotlinx.coroutines.flow.Flow

interface AuthorToBookRepository {
    fun insertBook(book: Book) : Long

    fun insertAuthor(author: Author) : Long

    fun insertAuthorToBook(authorToBook: AuthorToBook): Long

    fun getAuthorBook(authorId: Long): Flow<AuthorBook>

    fun getBookAuthor(bookId: Long): Flow<BookAuthor>

    fun updateAuthorToBook(authorToBook: AuthorToBook)

    fun deleteAuthorToBook(authorToBook: AuthorToBook)

    fun getAuthorToBook(authorId: Long, bookId: Long): AuthorToBook

    //fun getAllAuthorsStream(): Flow<List<Author>>
}