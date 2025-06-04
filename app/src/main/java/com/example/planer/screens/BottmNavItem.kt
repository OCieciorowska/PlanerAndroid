package com.example.planer.screens

sealed class BottomNavItem(val route: String, val label: String, val icon: String) {
    object Tasks : BottomNavItem("taskList", "Zadania", "📋")
    object Calendar : BottomNavItem("calendar", "Kalendarz", "📅")
    object Logout : BottomNavItem("logout", "Wyloguj", "🚪")
}
