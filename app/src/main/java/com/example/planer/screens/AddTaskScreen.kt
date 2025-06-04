package com.example.planer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planer.viewmodel.PlanerViewModel

@Composable
fun AddTaskScreen(navController: NavController, viewModel: PlanerViewModel) {
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Tytuł") })
        OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Opis") })
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Data (YYYY-MM-DD)") })
        OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Czas (HH:MM)") })
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            viewModel.addTask(title, desc, date, time)
            navController.popBackStack()
        }) {
            Text("Zapisz")
        }
    }
}
