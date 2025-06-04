package com.example.planer.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.planer.viewmodel.PlanerViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: PlanerViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Hasło") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (isRegistering) {
                    viewModel.register(email, password) { success, errorMessage ->
                        if (success) {
                            Toast.makeText(context, "Rejestracja zakończona", Toast.LENGTH_SHORT).show()
                            navController.navigate("taskList")
                        } else {
                            Toast.makeText(context, "Rejestracja nieudana: $errorMessage", Toast.LENGTH_LONG).show()
                        }
                    }

                } else {
                    viewModel.login(email, password) { success ->
                        if (success) {
                            viewModel.loadTasks() // <- WAŻNE: Ładowanie tylko zadań zalogowanego użytkownika
                            navController.navigate("taskList")
                        } else {
                            Toast.makeText(context, "Logowanie nieudane", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isRegistering) "Utwórz konto" else "Zaloguj się")
        }

        TextButton(
            onClick = { isRegistering = !isRegistering },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(if (isRegistering) "Masz już konto? Zaloguj się" else "Nie masz konta? Zarejestruj się")
        }
    }
}
