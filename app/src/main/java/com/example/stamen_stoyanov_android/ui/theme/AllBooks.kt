package com.example.stamen_stoyanov_android.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stamen_stoyanov_android.R
import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.entities.Book
import com.example.stamen_stoyanov_android.data.entities.BookAuthor
import com.example.stamen_stoyanov_android.data.repositores.AuthorToBookRepository
import com.example.stamen_stoyanov_android.data.repositores.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun DisplayAllBooks(navController: NavHostController,authorToBookRepository: AuthorToBookRepository,
               bookRepository: BookRepository,
               modifier: Modifier = Modifier) {
    val itemsFlow = bookRepository.getAllFlow()
    val books by itemsFlow.collectAsState(initial = emptyList())

    TopAppBarMenu(
        navController = navController,
        topBarTitle = stringResource(id = R.string.Всички_книги)
    )
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        BookList(
            books = books,
            navController = navController,
            modifier = modifier,
            authorToBookRepository = authorToBookRepository,
        )
    }
}

@Composable
fun BookList(books: List<Book>,
             navController: NavHostController,
             modifier: Modifier = Modifier,
             authorToBookRepository: AuthorToBookRepository,
             showOnlyTitleAndGenre: Boolean = false) {
    LazyColumn(modifier = modifier) {
        items(books) { book ->
            BookItems(
                bookEntity = book,
                navController = navController,
                modifier = modifier.padding(8.dp),
                authorToBookRepository = authorToBookRepository,
                showOnlyTitleAndGenre = showOnlyTitleAndGenre
            )
        }
    }
}
@Composable
fun BookDetail(
    bookId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authorToBookRepository: AuthorToBookRepository,
    bookRepository: BookRepository,
    context: android.content.Context
) {

    var book by remember {
        mutableStateOf<Book?>(
            Book(
                title = "",
                genre = "",
                publicationYear =2000,
            )
        )
    }
    var BookAuthorFlow: Flow<BookAuthor>
    var author by remember { mutableStateOf<Author?>(Author()) }
    var buttonClicked by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit)
    {
        CoroutineScope(Dispatchers.Default).launch {
            book = bookRepository.getById(bookId)

            BookAuthorFlow = authorToBookRepository.getBookAuthor(
                book!!.bookId.toLong()
            )
            author = BookAuthorFlow.firstOrNull()!!.authors.firstOrNull()!!
        }
    }
    TopAppBarMenu(
        navController = navController,
        topBarTitle = stringResource(id = R.string.Всички_книги)
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 180.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.Информация_за_книги),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "${stringResource(id = R.string.Заглавие)}: ${book?.title}")
                Text(text = "${stringResource(id = R.string.Жанр_на_книга)}: ${book?.genre}")
                Text(text = "Дата на публикуване: ${book?.publicationYear}")
                Text(text = "Автор: ${author?.firstName} ${author?.lastName}")
            }
        }
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Button(
                onClick = {
                    buttonClicked = true

                }) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Edit,
                    contentDescription = null
                )
            }
            if (buttonClicked) {
                navController.navigate("updateBook/${book?.bookId}")

                buttonClicked = false
            }
            Button(
                onClick = {

                    showDialog = true

                }) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                    contentDescription = null
                )
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(text = "Потвърждение")
                    },
                    text = {
                        Text(text = "Сигурни ли сте че искате да изтриете елемента?")
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                CoroutineScope(Dispatchers.Default).launch {

                                    try {
                                        bookRepository.delete(book!!)
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(
                                                context,
                                                "Успешно изтрито!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            navController.navigate("DisplayBooks")
                                        }
                                    } catch (e: Exception) {
                                        println(e.message)
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(
                                                context,
                                                "Изтриването неуспешно!",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                                showDialog = false
                            }
                        ) {
                            Text(text = "Изтрий")
                        }
                    },
                    dismissButton = { // След това "Отказ"
                        Button(
                            onClick = { showDialog = false }
                        ) {
                            Text(text = "Отказ")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BookItems(
    bookEntity: Book,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authorToBookRepository: AuthorToBookRepository,
    showOnlyTitleAndGenre: Boolean = false
) {

    var author by remember { mutableStateOf<Author?>(Author()) }
    LaunchedEffect(bookEntity) {
        val bookAuthorFlow = authorToBookRepository.getBookAuthor(bookEntity.bookId.toLong())
        val bookAuthor = bookAuthorFlow.firstOrNull()
        author = bookAuthor?.authors?.firstOrNull()
    }
    var cardClicked by remember { mutableStateOf(false) }

    if (cardClicked) {
        LaunchedEffect(bookEntity.bookId) {
            navController.navigate("book/${bookEntity.bookId}")
        }
        cardClicked = false
    }

    Card( onClick = {

        cardClicked = true
    },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.Информация_за_книги),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "${stringResource(id = R.string.Заглавие)}: ${bookEntity.title}")
                Text(text = "${stringResource(id = R.string.Жанр_на_книга)}: ${bookEntity.genre}")
                if (!showOnlyTitleAndGenre) {
                    Text(text = "${stringResource(id = R.string.Дата_на_публикуване)}: ${bookEntity.publicationYear}")
                    Text(text = "${stringResource(id = R.string.Автор_на_книга)}: ${author?.firstName} ${author?.lastName}")
                }
            }
        }
    }
}

