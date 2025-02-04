package com.example.stamen_stoyanov_android.ui.theme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMenu(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    topBarTitle: String
) {
    var expanded by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = topBarTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More..."
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Главно меню") },
                    onClick = {
                        expanded = false
                        navController.navigate("Main")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Добави Автор") },
                    onClick = {
                        expanded = false
                        navController.navigate("insertAuthor")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Добави книга") },
                    onClick = {
                        expanded = false
                        navController.navigate("InsertBook")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Покажи автори") },
                    onClick = {
                        expanded = false
                        navController.navigate("DisplayAll")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Покажи книги") },
                    onClick = {
                        expanded = false
                        navController.navigate("DisplayBooks")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Търси книги") },
                    onClick = {
                        expanded = false
                        navController.navigate("DisplayByAuthor")
                    }
                )
            }
        }
    )
}