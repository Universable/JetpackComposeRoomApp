package com.example.stamen_stoyanov_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.stamen_stoyanov_android.ui.theme.Android_app
import com.example.stamen_stoyanov_android.ui.theme.NavigationGraph

@Composable
fun app() : BookStoreApp = LocalContext.current.applicationContext as BookStoreApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Android_app {
                Application()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Application() {
    val navigationController = rememberNavController()

    val modifier: Modifier = Modifier

    NavigationGraph(navigationController,modifier)

}