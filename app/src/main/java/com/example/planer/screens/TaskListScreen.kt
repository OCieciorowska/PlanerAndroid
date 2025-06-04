package com.example.planer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planer.viewmodel.PlanerViewModel

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

        tasks.forEach { task ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(task.title, style = MaterialTheme.typography.titleMedium)
                    Text(task.description)
                    Text("${task.date} ${task.time}")

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { viewModel.toggleTask(task) }) {
                            Text(if (task.isCompleted) "Cofnij ukończenie" else "Zakończ")
                        }
                        TextButton(onClick = { viewModel.deleteTask(task) }) {
                            Text("Usuń", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

