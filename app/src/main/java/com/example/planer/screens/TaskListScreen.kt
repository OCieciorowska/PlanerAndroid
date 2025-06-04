package com.example.planer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planer.viewmodel.PlanerViewModel


import androidx.compose.material3.SwipeToDismissBox

import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState

import androidx.compose.ui.Alignment



//import androidx.compose.material3.rememberDismissState
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(navController: NavController, viewModel: PlanerViewModel) {
    val tasks by viewModel.tasks.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Button(onClick = { navController.navigate("addTask") }) {
            Text("Dodaj Zadanie")
        }
        //Button(onClick = { navController.navigate("calendar") }) {
         //   Text("Kalendarz")
        //}

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(tasks, key = { it.id }) { task ->
                val dismissState = rememberSwipeToDismissBoxState(
                    positionalThreshold = { 150f },
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart || it == SwipeToDismissBoxValue.StartToEnd) {
                            viewModel.deleteTask(task)
                            true
                        } else false
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {},
                    content = {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { viewModel.toggleTask(task) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(task.title)
                                    Text(task.description)
                                    Text("${task.date} ${task.time}")
                                }
                                Checkbox(
                                    checked = task.isCompleted,
                                    onCheckedChange = {
                                        viewModel.toggleTask(task)
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Aby usunąć zadanie przeciągnij w lewo",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}