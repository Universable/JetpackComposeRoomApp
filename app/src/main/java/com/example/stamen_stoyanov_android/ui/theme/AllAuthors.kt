package com.example.stamen_stoyanov_android.ui.theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.stamen_stoyanov_android.R
import com.example.stamen_stoyanov_android.data.entities.Author
import com.example.stamen_stoyanov_android.data.repositores.AuthorRepository
import com.example.stamen_stoyanov_android.data.repositores.AuthorToBookRepository


@Composable
fun DisplayAll(navController: NavHostController,
               repository: AuthorRepository,
               modifier: Modifier = Modifier,
               authorToBookRepository: AuthorToBookRepository) {
    val itemsFlow = repository.getaAllAuthorsFlow()
    val authors by itemsFlow.collectAsState(initial = emptyList())
    TopAppBarMenu(navController = navController, topBarTitle = "Всички автори")
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        AuthorList(
            authors = authors,
            navController = navController,
            modifier = modifier,
            authorToBookRepository = authorToBookRepository,
        )
    }
}

@Composable
fun AuthorList(authors: List<Author>,
               navController: NavHostController,
               modifier: Modifier = Modifier,
               authorToBookRepository: AuthorToBookRepository,) {
    LazyColumn(modifier = modifier) {
        items(authors) { author ->
            Author(
                author = author,
                navController = navController,
                modifier = modifier.padding(8.dp)
            )
        }
    }
}
@Composable
fun Author(author: Author, modifier: Modifier = Modifier, navController: NavHostController) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.Информация_за_автора),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = "${stringResource(id = R.string.Име)}:${author.firstName}")
                Text(text = "${stringResource(id = R.string.Фамилия)}: ${author.lastName}")
                Text(text = "${stringResource(id = R.string.Страна)}: ${author.country}")
            }
        }
    }
}
