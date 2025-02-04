package com.example.stamen_stoyanov_android.data.dao

import android.adservices.adid.AdId
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.stamen_stoyanov_android.data.entities.AuthorToBook
import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.entities.AuthorBook
import com.example.stamen_stoyanov_android.data.entities.Book
import com.example.stamen_stoyanov_android.data.entities.BookAuthor
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorBookDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAuthor(author: Author) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBook(book: Book) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthorToBook(authorToBook: AuthorToBook): Long

    @Query("SELECT * FROM books WHERE bookId = :bookId")
    fun getBookAuthor(bookId: Long): Flow<BookAuthor>

    @Query("SELECT * FROM authors WHERE authorId = :authorId")
    fun getAuthorBook(authorId: Long): Flow<AuthorBook>

    @Query("SELECT * FROM AuthorToBook WHERE authorId = :authorId and bookId = :bookId")
    fun getAuthorToBook(authorId: Long,bookId: Long) : AuthorToBook

    @Update
    fun updateAuthorToBook(authorToBook: AuthorToBook)

    @Delete
    fun deleteAuthorToBook(authorToBook: AuthorToBook)


}