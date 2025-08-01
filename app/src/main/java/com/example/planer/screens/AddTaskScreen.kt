package com.example.planer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController

import java.util.Calendar


import com.example.planer.viewmodel.PlanerViewModel



@OptIn(ExperimentalMaterial3Api::class)

@Composable
/*
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
}*/
fun AddTaskScreen(navController: NavController, viewModel: PlanerViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            date = "%02d/%02d/%d".format(day, month + 1, year)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            time = "%02d:%02d".format(hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // 24-hour format
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Tytuł") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Opis") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { datePickerDialog.show() }) {
            Text(if (date.isEmpty()) "Wybierz datę" else "Data: $date")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { timePickerDialog.show() }) {
            Text(if (time.isEmpty()) "Wybierz godzinę" else "Godzina: $time")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isBlank() || description.isBlank() || date.isBlank() || time.isBlank()) {
                    Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addTask(title, description, date, time)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dodaj zadanie")
        }
    }
}