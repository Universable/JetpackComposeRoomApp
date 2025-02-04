package com.example.stamen_stoyanov_android.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["authorId", "bookId"])
data class AuthorToBook(
    val authorId: Int,
    val bookId: Int
)