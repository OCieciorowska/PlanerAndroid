package com.example.planer.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.planer.screens.*
import com.example.planer.viewmodel.PlanerViewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding

import androidx.compose.ui.unit.dp


@Composable
fun PlanerApp() {
    val navController = rememberNavController()
    val viewModel = PlanerViewModel()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") { LoginScreen(navController, viewModel) }
            composable("taskList") { TaskListScreen(navController, viewModel) }
            composable("addTask") { AddTaskScreen(navController, viewModel) }
            composable("calendar") { CalendarScreen(navController, viewModel) }
        }
    }
}