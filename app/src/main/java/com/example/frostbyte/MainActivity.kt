package com.example.frostbyte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.frostbyte.data.AppContainer
import com.example.frostbyte.navigation.AppNavGraph
import com.example.frostbyte.ui.theme.FrostByte


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            FrostByte {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FrostByteApp()
                }
            }
        }
    }
}

/**
 * Main UI composable that builds the screen.
 */
@Composable
fun FrostByteApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val appContainer = AppContainer(context)

    AppNavGraph(
        navController = navController,
        appContainer = appContainer
    )
}