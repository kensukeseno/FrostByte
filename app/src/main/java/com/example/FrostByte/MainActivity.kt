package com.example.FrostByte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.FrostByte.navigation.AppNavGraph
import com.example.FrostByte.ui.theme.FrostByte


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FrostByte {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FrostByteApp()
                }
            }
        }
    }
}

/**
 * Main UI composable that builds the Tic Tac Toe screen.
 */
@Composable
fun FrostByteApp() {
    val navController = rememberNavController()
    AppNavGraph(navController)
}