package com.example.stamen_stoyanov_android.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.stamen_stoyanov_android.data.entities.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Update
    fun update(book: Book)

    @Delete
    fun delete(book: Book)

    @Query("SELECT * FROM books WHERE bookId = :id")
    fun getBookById(id: Int): Book

    @Query("SELECT * FROM books ORDER by title desc")
    fun getAll(): List<Book>

    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getallBooks(): Flow<List<Book>>
}