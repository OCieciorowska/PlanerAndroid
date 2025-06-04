package com.example.planer.model

data class Tasks(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var date: String = "",       // YYYY-MM-DD
    var time: String = "",       // HH:MM
    var userId: String = "",
    var isCompleted: Boolean = false
)
