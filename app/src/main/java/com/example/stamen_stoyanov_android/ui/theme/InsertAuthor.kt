package com.example.stamen_stoyanov_android.ui.theme
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stamen_stoyanov_android.app
import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.entities.Book
import com.example.stamen_stoyanov_android.data.repositores.AuthorRepository
import com.example.stamen_stoyanov_android.data.repositores.AuthorToBookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
    fun InsertAuthor(navController: NavHostController,
                     repository: AuthorToBookRepository,
                     modifier: Modifier = Modifier,
                     context: Context) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    fun isValidName(name: String): Boolean {
        val regex = "^[A-Za-zА-Яа-яЁё ]+$".toRegex()
        return name.trim().length >= 2 && regex.matches(name.trim())
    }
    TopAppBarMenu(navController = navController, topBarTitle = "Създай Автор")
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
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "First Name",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
            TextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                singleLine = true,
                placeholder = {
                    Text(text = "Въведете име", color = Color.Gray)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )

                    .padding(horizontal = 8.dp)
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Family Name",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = { Text(text = "Въведете фамилия", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Country",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            )
            TextField(
                value = country,
                onValueChange = { country = it },
                placeholder = { Text(text = "Въведете държава", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || country.isBlank()) {
                        Toast.makeText(
                            context,
                            "Моля, попълнете всички полета.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (!isValidName(firstName)) {
                        Toast.makeText(
                            context,
                            "Първото име трябва да съдържа само букви и да е поне 2 символа.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (!isValidName(lastName)) {
                        Toast.makeText(
                            context,
                            "Фамилията трябва да съдържа само букви и да е поне 2 символа.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (!isValidName(country)) {
                        Toast.makeText(
                            context,
                            "Държавата трябва да съдържа само букви и да е поне 2 символа.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        CoroutineScope(Dispatchers.IO).launch {
                            val authorId: Long = repository.insertAuthor(
                                Author(
                                    firstName = firstName,
                                    lastName = lastName,
                                    country = country
                                )
                            )
                            if (authorId > 0) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    Toast.makeText(
                                        context,
                                        "Авторът е записан успешно.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                }
                            } else {
                                CoroutineScope(Dispatchers.Main).launch {
                                    Toast.makeText(
                                        context,
                                        "Възникна грешка при записа.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 1.dp).width(250.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Запис")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayBooksbyAuthor(modifier: Modifier = Modifier, navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    var selectedAuthor by remember { mutableStateOf<Author?>(null) }
    val authors = remember { mutableStateListOf<Author>() }
    val books = remember { mutableStateListOf<Book>() }

    val authorRepository = app().container.authorRepository
    val authorToBookRepository = app().container.authorToBookRepository
    var displayCards by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        AuthorList(authors, authorRepository)
    }


    TopAppBarMenu(navController = navController, topBarTitle = "Търси книга")

    Column(
        modifier = modifier.fillMaxWidth()
        .padding(top = 110.dp)
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier.padding(top = 8.dp)
        ) {
            TextField(
                value = selectedAuthor?.let { "${it.firstName} ${it.lastName}" } ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Избор на автор") },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",
                        tint = Color.Gray
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },


            ) {
                authors.forEach { author ->
                    DropdownMenuItem(
                        text = { Text("${author.firstName} ${author.lastName}") },
                        onClick = {
                            selectedAuthor = author
                            expanded = false

                            CoroutineScope(Dispatchers.IO).launch()
                            {
                                books.clear()
                                displayCards = false
                                loadAuthors(
                                    authorToBookRepository,
                                    selectedAuthor,
                                    books,
                                )
                                displayCards = true
                            }
                        }
                    )
                }
            }
        }

        if (displayCards) {
            BookList(
                books = books,
                navController = navController,
                modifier = modifier,
                authorToBookRepository = authorToBookRepository,
                showOnlyTitleAndGenre = true
            )
        }
    }
}

suspend fun loadAuthors(
    authorToBookRepository: AuthorToBookRepository,
    selectedAuthor: Author?,
    books: MutableList<Book>,
) {

    val AuthorFlow = authorToBookRepository.getAuthorBook(selectedAuthor!!.authorId.toLong())
    val authorBook = AuthorFlow.firstOrNull()

    if (authorBook != null) {
        withContext(Dispatchers.Main) {
            books.addAll(authorBook.books)
        }
    }
}
fun AuthorList(authorList: MutableList<Author>, repository: AuthorRepository) {

    CoroutineScope(Dispatchers.IO).launch {

        var authors: List<Author> = repository.getAll()

        withContext(Dispatchers.Main) {
            authorList.clear()
            authorList.addAll(authors)
        }
    }
}