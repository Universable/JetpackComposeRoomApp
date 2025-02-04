package com.example.stamen_stoyanov_android.data.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class BookAuthor (
    @Embedded
    val book: Book,
    @Relation(
        parentColumn ="bookId",
        entityColumn = "authorId",
        associateBy = Junction(AuthorToBook::class)
    )
    val authors: List<Author>
)