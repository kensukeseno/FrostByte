package com.example.frostbyte.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.frostbyte.ui.components.Header
import com.example.frostbyte.viewmodel.HomeViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp
import com.example.frostbyte.ui.components.ListCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var listName by remember { mutableStateOf("") }

    val lists by homeViewModel.allLists.collectAsState(initial = emptyList())

    Scaffold(
        //FAB pinned to bottom right corner
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add List",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            //showBack = false to remove the back button from view
            Header(navController = navController, title = "Task Lists", showBack = false)

        //lazy column
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(lists) { list ->
                    ListCard(
                        listTitle = list.name,
                        onClick = { navController.navigate("list/${list.listId}") },
                        onDelete = { homeViewModel.deleteList(list) }
                    )
                }
            }
        }
    }
        if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                    showDialog = false
                    listName = ""
                },
                title = { Text(
                    "Add New List",
                    color = MaterialTheme.colorScheme.secondary
                ) },
                text = {
                    TextField(
                        value = listName,
                        onValueChange = { listName = it },
                        label = { Text("List Name") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (listName.isNotBlank()) {
                                homeViewModel.addList(listName.trim())
                            }
                            showDialog = false
                            listName = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showDialog = false
                        listName = ""
                    },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.errorContainer
                        )) {
                        Text("Cancel")
                    }
                }
            )
        }
}


