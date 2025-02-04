package com.example.stamen_stoyanov_android.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.stamen_stoyanov_android.data.entities.Author
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthorDao {
    @Update
    fun updateAuthor(author: Author)

    @Delete
    fun deleteAuthor(author: Author)

    @Query("SELECT * FROM authors WHERE authorId = :id")
    fun getAuthorById(id: Int): Author

    @Query("SELECT * FROM authors")
    fun getallAuthors(): List<Author>

    @Query("SELECT * FROM authors ORDER BY firstName ASC")
    fun getaAllAuthorsFlow(): Flow<List<Author>>
}
