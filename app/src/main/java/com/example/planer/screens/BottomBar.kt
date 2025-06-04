package com.example.planer.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.planer.viewmodel.PlanerViewModel

@Composable
fun BottomBar(navController: NavController, viewModel: PlanerViewModel) {
    val items = listOf(
        BottomNavItem.Tasks,
        BottomNavItem.Calendar,
        BottomNavItem.Logout
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = false,
                onClick = {
                    if (item is BottomNavItem.Logout) {
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true } // Czyści backstack
                        }
                    } else {
                        navController.navigate(item.route)
                    }
                },
                icon = { Text(item.icon) },
                label = { Text(item.label) }
            )
        }
    }
}

