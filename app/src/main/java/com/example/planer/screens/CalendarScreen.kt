package com.example.planer.screens

import android.app.DatePickerDialog
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import android.widget.CalendarView

import androidx.compose.ui.viewinterop.AndroidView



/*
@SuppressLint("SimpleDateFormat")
@Composable
fun CalendarScreen(navController: NavController, viewModel: PlanerViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    val context = LocalContext.current

    // Aktualna data i stan
    var selectedDate by remember { mutableStateOf(today()) }

    // Picker do daty
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            val formatted = SimpleDateFormat("yyyy-MM-dd").format(
                GregorianCalendar(year, month, day).time
            )
            selectedDate = formatted
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val filteredTasks = tasks.filter { it.date == selectedDate }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Wybierz datę:", style = MaterialTheme.typography.titleMedium)

        OutlinedButton(
            onClick = { datePickerDialog.show() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Data: $selectedDate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Zadania na $selectedDate:", style = MaterialTheme.typography.titleMedium)

        if (filteredTasks.isEmpty()) {
            Text(
                "Brak zadań w tym dniu.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            filteredTasks.forEach { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { viewModel.toggleTask(task) },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(task.title, style = MaterialTheme.typography.titleSmall)
                        Text(task.description, style = MaterialTheme.typography.bodyMedium)
                        Text("Godzina: ${task.time}", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

fun today(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}
*/

@SuppressLint("SimpleDateFormat")
@Composable
fun CalendarScreen(navController: NavController, viewModel: PlanerViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var selectedDate by remember { mutableStateOf(today()) }

    val sdf = SimpleDateFormat("yyyy-MM-dd")

    val filteredTasks = tasks.filter { it.date == selectedDate }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Kalendarz", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Graficzny kalendarz
        AndroidView(
            factory = { context ->
                CalendarView(context).apply {
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        val cal = GregorianCalendar(year, month, dayOfMonth)
                        selectedDate = sdf.format(cal.time)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Zadania na $selectedDate:", style = MaterialTheme.typography.titleMedium)

        if (filteredTasks.isEmpty()) {
            Text(
                "Brak zadań w tym dniu.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            filteredTasks.forEach { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { viewModel.toggleTask(task) },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(task.title, style = MaterialTheme.typography.titleSmall)
                        Text(task.description, style = MaterialTheme.typography.bodyMedium)
                        Text("Godzina: ${task.time}", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

fun today(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}
