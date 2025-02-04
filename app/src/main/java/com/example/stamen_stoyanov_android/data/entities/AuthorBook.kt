package com.example.stamen_stoyanov_android.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class AuthorBook (
    @Embedded
    val author: Author,
    @Relation(
        parentColumn ="authorId",
        entityColumn = "bookId",
        associateBy = Junction(AuthorToBook::class)
    )
    val books: List<Book>
)