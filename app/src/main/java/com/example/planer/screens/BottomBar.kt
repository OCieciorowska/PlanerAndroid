package com.example.planer.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        val items = listOf(BottomNavItem.Tasks, BottomNavItem.Calendar)

        items.forEach { item ->
            NavigationBarItem(
                selected = false, // Możesz dodać logikę do podświetlania aktywnego ekranu
                onClick = { navController.navigate(item.route) },
                icon = { Text(item.icon) },
                label = { Text(item.label) }
            )
        }
    }
}
