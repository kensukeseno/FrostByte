package com.example.FrostByte

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

}



/**
 * Composable that displays what the UI of the app looks like in light theme in the design tab.
 */
@Preview
@Composable
fun FrostBytePreview() {
    FrostByte{
        FrostByteApp()
    }
}