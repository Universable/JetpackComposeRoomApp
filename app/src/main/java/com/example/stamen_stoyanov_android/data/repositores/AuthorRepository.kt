package com.example.stamen_stoyanov_android.data.repositores

import com.example.stamen_stoyanov_android.data.entities.Author
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {
    fun update(author: Author)

    fun delete (author: Author)

    fun getById(id: Int): Author

    fun getAll(): List<Author>

    fun getaAllAuthorsFlow(): Flow<List<Author>>

}