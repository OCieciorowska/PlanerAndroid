package com.example.planer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExposedDropdownMenuBox

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api

import android.app.DatePickerDialog
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

    // DATA
    var date by remember { mutableStateOf("") }
    val context = LocalContext.current

    // GODZINA
    val hours = listOf("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00")
    var expanded by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableStateOf(hours[0]) }

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

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Tytuł") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Opis") })

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { datePickerDialog.show() }) {
            Text(if (date.isEmpty()) "Wybierz datę" else "Data: $date")
        }

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedHour,
                onValueChange = {},
                readOnly = true,
                label = { Text("Godzina") },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                hours.forEach { hour ->
                    DropdownMenuItem(
                        text = { Text(hour) },
                        onClick = {
                            selectedHour = hour
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.addTask(title, description, date, selectedHour)
            navController.popBackStack()
        }) {
            Text("Dodaj zadanie")
        }
    }
}
