package com.example.stamen_stoyanov_android.data.repositores

import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.entities.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun update(book: Book)

    fun delete (book: Book)

    fun getById(id: Int): Book

    fun getAll(): List<Book>

    fun getAllFlow(): Flow<List<Book>>
}