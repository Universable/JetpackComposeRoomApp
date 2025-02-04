package com.example.stamen_stoyanov_android.ui.theme
import android.app.DatePickerDialog
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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar


fun АuthorListItems(authorListItems: MutableList<Author>, repository: AuthorRepository) {

    CoroutineScope(Dispatchers.IO).launch {

        val authors: List<Author> = repository.getAll()

        withContext(Dispatchers.Main) {
            authorListItems.clear()
            authorListItems.addAll(authors)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertBook(
    navController: NavHostController,
    authorRepository: AuthorRepository,
    authorToBookRepository: AuthorToBookRepository,
    context: Context,
    modifier: Modifier = Modifier
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
    val fieldVerticalPadding = 8.dp

    LaunchedEffect(Unit) {
        АuthorListItems(authors, authorRepository)
    }

    TopAppBarMenu(navController = navController, topBarTitle = "Създаване на книга")

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
                onValueChange = { title = it },
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
                onValueChange = { publicationYear = it },
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
                fieldStatus[0] = title.isNotBlank()
                fieldStatus[1] = genre != null
                fieldStatus[2] = selectedAuthor != null
                fieldStatus[3] =
                    publicationYear.isNotBlank() && publicationYear.toIntOrNull() != null
                CoroutineScope(Dispatchers.Default).launch {
                    if (fieldStatus.all { it }) {
                        val resultId: Long = authorToBookRepository.insertBook(
                            Book(
                                title = title,
                                genre = genre?.name ?: "",
                                publicationYear = publicationYear.toInt()
                            )
                        )

                        if (resultId > 0) {
                            val authorToBook =
                                AuthorToBook(selectedAuthor!!.authorId, resultId.toInt())
                            authorToBookRepository.insertAuthorToBook(authorToBook)

                            withContext(Dispatchers.Main) {
                                navController.popBackStack()
                                Toast.makeText(context, "Успешен запис!", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Грешка при запис!", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "Има непопълнени или невалидни полета!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        ) { Text(text = "Запази") }
    }
}
@Composable
fun YearPickerDialog(
    context: Context,
    onYearSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)

    LaunchedEffect(true) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, _, _ ->
                onYearSelected(selectedYear)
            },
            currentYear,
            0,
            1
        )
        val minYear = 1850
        val maxYear = currentYear + 0

        datePickerDialog.datePicker.minDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, minYear)
        }.timeInMillis

        datePickerDialog.datePicker.maxDate = Calendar.getInstance().apply {
            set(Calendar.YEAR, maxYear)
        }.timeInMillis

        datePickerDialog.setOnDismissListener {
            onDismiss()
        }
        datePickerDialog.show()
    }
}
suspend fun getBookAuthor(
    book: Book,
    authorToBookRepository: AuthorToBookRepository,

    ): Author? {
    return authorToBookRepository.getBookAuthor(book.bookId.toLong()).firstOrNull()
        ?.authors?.firstOrNull()
}


fun setBookById(bookId: Int, bookRepository: BookRepository): Book {
    return bookRepository.getById(bookId)

}