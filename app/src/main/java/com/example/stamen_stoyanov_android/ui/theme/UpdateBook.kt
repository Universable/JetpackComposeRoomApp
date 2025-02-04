package com.example.stamen_stoyanov_android.ui.theme
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.entities.AuthorToBook
import com.example.stamen_stoyanov_android.data.entities.Book
import com.example.stamen_stoyanov_android.data.entities.Genre
import com.example.stamen_stoyanov_android.data.repositores.AuthorRepository
import com.example.stamen_stoyanov_android.data.repositores.AuthorToBookRepository
import com.example.stamen_stoyanov_android.data.repositores.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateBook(
    navController: NavHostController,
    authorRepository: AuthorRepository,
    authorToBookRepository: AuthorToBookRepository,
    bookRepository: BookRepository,
    context: Context,
    modifier: Modifier = Modifier,
    bookId : Int
) {
    var title by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf<Genre?>(null) }
    var publicationYear by remember { mutableStateOf("") }
    val authors = remember { mutableStateListOf<Author>() }
    var expandedGenre by remember { mutableStateOf(false) }
    var expandedAuthor by remember { mutableStateOf(false) }
    var selectedAuthor by remember { mutableStateOf<Author?>(null) }
    var isAuthorNonEmpty by remember { mutableStateOf(true) }
    var isAuthorSelected by remember { mutableStateOf(true) }
    val fieldStatus = remember { mutableStateListOf(false, false, false, false, false) }
    var showYearPicker by remember { mutableStateOf(false) }
    var book by remember { mutableStateOf(Book(0, "----", "----", 0)) }
    var updateSuccessful by remember { mutableStateOf(false) }
    var authorsOfBooks by remember { mutableStateOf(Author()) }
    val isTitleValid by remember { mutableStateOf(true) }
    var isGenreValid by remember { mutableStateOf(true) }
    var isPubYearValid by remember { mutableStateOf(true) }
    val fieldNotEmptyStatus = remember { mutableStateListOf(false, false, false, false, false) }
    val fieldVerticalPadding = 8.dp
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            book = setBookById(bookId, bookRepository)
            val fetchedAuthor = getBookAuthor(book, authorToBookRepository)

            if (fetchedAuthor != null) {
                authorsOfBooks = fetchedAuthor
            }
            withContext(Dispatchers.Main) {
                title = book.title
                genre = Genre.entries.find { it.label == book.genre }
                publicationYear = book.publicationYear.toString()
                selectedAuthor = authors.find { it.authorId == authorsOfBooks.authorId }
                fieldNotEmptyStatus[0] = title.isNotEmpty()
                fieldNotEmptyStatus[1] = genre != null
                fieldNotEmptyStatus[2] = publicationYear.isNotEmpty()
            }
        }
        АuthorListItems(authors, authorRepository)
    }
    TopAppBarMenu(navController = navController, topBarTitle = "Промяна на книга")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = fieldVerticalPadding)
        ) {
            Text(
                text = "Заглавие",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
            TextField(
                value = title,
                onValueChange = { newValue ->
                    if (newValue.all {
                            (it.isLetter()
                                    || it.isWhitespace()
                                    || it == '-')
                        }
                    ) {
                        title = newValue
                    }
                    fieldNotEmptyStatus[0] = newValue.isNotEmpty()
                },
                placeholder = { Text(text = "Въведете заглавие", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = fieldVerticalPadding)
        ) {
            Text(
                text = "Жанр",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
            ExposedDropdownMenuBox(
                expanded = expandedGenre,
                onExpandedChange = { expandedGenre = !expandedGenre },
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = genre?.label ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenre)
                    },
                    label = { Text("Избор на жанр") },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedGenre,
                    onDismissRequest = { expandedGenre = false }
                ) {
                    Genre.entries.forEach { genreItem ->
                        DropdownMenuItem(
                            text = { Text(genreItem.label) },
                            onClick = {
                                genre = genreItem
                                expandedGenre = false
                            }
                        )
                    }
                }
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = fieldVerticalPadding)
        ) {
            Text(
                text = "Автор",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
            ExposedDropdownMenuBox(
                expanded = expandedAuthor,
                onExpandedChange = { expandedAuthor = !expandedAuthor },
                modifier = modifier.padding(top = fieldVerticalPadding).fillMaxWidth()
            ) {
                TextField(
                    value = selectedAuthor?.let { "${it.firstName} ${it.lastName}" } ?: "",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGenre)
                    },
                    label = {
                        Text(
                            "Избор на автор",
                        )
                    },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedAuthor,
                    onDismissRequest = { expandedAuthor = false }
                ) {
                    isAuthorNonEmpty = authors.isNotEmpty()
                    isAuthorSelected = selectedAuthor != null
                    fieldStatus[4] = authors.isNotEmpty()

                    authors.forEach { author ->
                        DropdownMenuItem(
                            text = { Text("${author.firstName} ${author.lastName}") },
                            onClick = {
                                selectedAuthor = author
                                expandedAuthor = false
                            }
                        )
                    }
                }
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = fieldVerticalPadding)
        ) {
            Text(
                text = "Година",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
            TextField(
                value = publicationYear,
                onValueChange = { newValue ->
                    publicationYear = newValue
                    fieldNotEmptyStatus[2] = newValue.isNotEmpty()
                },
                placeholder = { Text(text = "Изберете година", color = Color.Gray) },
                readOnly = true,
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color = Color.LightGray, shape = CircleShape)
                            .clickable { showYearPicker = true }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Calendar",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showYearPicker = true }
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
        }

        if (showYearPicker) {
            YearPickerDialog(
                context = LocalContext.current,
                onYearSelected = { selectedYear ->
                    publicationYear = selectedYear.toString()
                    showYearPicker = false
                },
                onDismiss = { showYearPicker = false }
            )
        }
        Button(
            modifier = modifier.padding(top = 17.dp).width(250.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White),
            onClick = {

                CoroutineScope(Dispatchers.Default).launch {
                    fieldNotEmptyStatus[1] = genre != null && genre?.label?.isNotEmpty() == true
                    for (bool: Boolean in fieldNotEmptyStatus) {
                        println(bool)
                    }
                    val validityList = listOf(isTitleValid, isGenreValid, isGenreValid)
                    validityList.forEach {
                        println(it)
                    }
                    val updateTitle = if (fieldNotEmptyStatus[0]) title else book.title

                    val updateGenre = if (fieldNotEmptyStatus[1]) {
                        genre?.label ?: book.genre
                    } else {
                        book.genre
                    }
                    val updatePubYear = if (publicationYear.isNotEmpty()) {
                        publicationYear.toIntOrNull()?.takeIf { it > 1900 && it <= Calendar.getInstance().get(
                            Calendar.YEAR) }
                            ?: 2009
                    } else {
                        2009
                    }
                    val isAuthorUpdated = selectedAuthor != null
                    val updatedBook = Book(bookId, updateTitle,updateGenre,updatePubYear)
                    val updatedAuthor = selectedAuthor ?: authorsOfBooks
                    if (listOf(isTitleValid, isGenreValid, isPubYearValid).all { it }) {
                        try {
                            bookRepository.update(updatedBook)

                            if (isAuthorUpdated) {

                                val authorToBook = AuthorToBook(authorsOfBooks.authorId, bookId)

                                authorToBook.let { authorToBookRepository.deleteAuthorToBook(authorToBook) }


                                val newauthorToBook =
                                    AuthorToBook(updatedAuthor.authorId, updatedBook.bookId)
                                authorToBookRepository.insertAuthorToBook(newauthorToBook)
                            }

                            updateSuccessful = true

                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(context, "Успешна промяна!", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                        catch (e: Exception) {
                            Log.e("UpdateBook", "Error updating book: ${e.message}")
                            println(e.message)
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(context, "Грешка при промяна!", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main)
                        {

                            if (!fieldNotEmptyStatus[1])
                                isGenreValid = !fieldNotEmptyStatus[1]
                            isPubYearValid = !fieldNotEmptyStatus[2]
                            Toast.makeText(
                                context,
                                "Има непопълнени или невалидни полета!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            })
        {
            Text(text = "Промени")
        }
        if (updateSuccessful) {
            navController.navigate("DisplayBooks")
            updateSuccessful = false
        }
    }
}
