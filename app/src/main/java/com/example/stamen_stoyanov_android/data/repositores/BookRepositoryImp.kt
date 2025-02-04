package com.example.stamen_stoyanov_android.data.repositores

import com.example.stamen_stoyanov_android.data.dao.BookDao
import com.example.stamen_stoyanov_android.data.entities.Book
import kotlinx.coroutines.flow.Flow

class BookRepositoryImp(private val bookDao: BookDao) : BookRepository {
    override fun update(book: Book) = bookDao.update(book)

    override fun delete(book: Book) = bookDao.delete(book)

    override fun getById(id:Int) : Book  =bookDao.getBookById(id)

    override fun getAll(): List<Book> = bookDao.getAll()

    override fun getAllFlow(): Flow<List<Book>> = bookDao.getallBooks()

}