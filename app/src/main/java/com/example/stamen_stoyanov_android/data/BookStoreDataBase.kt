package com.example.stamen_stoyanov_android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stamen_stoyanov_android.data.dao.AuthorBookDao
import com.example.stamen_stoyanov_android.data.dao.AuthorDao
import com.example.stamen_stoyanov_android.data.dao.BookDao
import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.entities.AuthorToBook
import com.example.stamen_stoyanov_android.data.entities.Book

@Database(
    entities = [Author::class, Book::class, AuthorToBook::class],
    version = 1,
    exportSchema = false
)

abstract class BookStoreDataBase : RoomDatabase(){
    abstract fun authorDao(): AuthorDao
    abstract fun bookDao(): BookDao
    abstract fun authorBookDao(): AuthorBookDao


    companion object{
        private var Instance: BookStoreDataBase? =null

        fun getDatabase(context: Context) : BookStoreDataBase{
            return Instance?: synchronized(this){
                Room.databaseBuilder(
                    context,
                    BookStoreDataBase::class.java,
                    "inventory_db"
                ).build().also { Instance = it }
            }
        }
    }
}