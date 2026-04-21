package com.example.frostbyte.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.frostbyte.R
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(navController: NavController, title: String, showBack: Boolean = true) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (showBack) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )

                IconButton(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.size(68.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon),
                        contentDescription = "Home",
                        modifier = Modifier.size(56.dp)
                    )
                }
            }

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.05f),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}




