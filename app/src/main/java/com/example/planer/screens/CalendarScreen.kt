package com.example.planer.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planer.viewmodel.PlanerViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@Composable
fun CalendarScreen(navController: NavController, viewModel: PlanerViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var selectedDate by remember { mutableStateOf(today()) }

    val filteredTasks = tasks.filter { it.date == selectedDate }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Wybierz datę:", style = MaterialTheme.typography.titleMedium)

        // Tu użyjemy prostego tekstowego wyboru daty – nie kalendarza
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { selectedDate = it },
            label = { Text("Data (yyyy-MM-dd)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Zadania na $selectedDate:", style = MaterialTheme.typography.titleMedium)
        filteredTasks.forEach { task ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { viewModel.toggleTask(task) }) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(task.title)
                    Text(task.description)
                    Text(task.time)
                }
            }
        }
    }
}

fun today(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}
