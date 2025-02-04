package com.example.stamen_stoyanov_android.data

import android.content.Context
import com.example.stamen_stoyanov_android.data.repositores.AuthorRepository
import com.example.stamen_stoyanov_android.data.repositores.AuthorRepositoryImp
import com.example.stamen_stoyanov_android.data.repositores.AuthorToBookRepository
import com.example.stamen_stoyanov_android.data.repositores.AuthorToBookRepositoryImp
import com.example.stamen_stoyanov_android.data.repositores.BookRepository
import com.example.stamen_stoyanov_android.data.repositores.BookRepositoryImp

class AppDataContainer(private val context: Context) {
    val authorRepository: AuthorRepository by lazy {
        AuthorRepositoryImp(BookStoreDataBase.getDatabase(context).authorDao())
    }
    val authorToBookRepository: AuthorToBookRepository by lazy {
        AuthorToBookRepositoryImp(BookStoreDataBase.getDatabase(context).authorBookDao())
    }
    val bookRepository: BookRepository by lazy {
        BookRepositoryImp(BookStoreDataBase.getDatabase(context).bookDao())
    }

}