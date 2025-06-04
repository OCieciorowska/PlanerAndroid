package com.example.planer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.planer.model.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Locale

class PlanerViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val _tasks = MutableStateFlow<List<Tasks>>(emptyList())
    val tasks = _tasks.asStateFlow()

    init {
        auth.addAuthStateListener {
            if (it.currentUser != null) loadTasks()
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loadTasks() // dodaj to!
                }
                onResult(task.isSuccessful)
            }
    }

    fun register(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorMessage = task.exception?.message
                    onResult(false, errorMessage)
                }
            }
    }



    fun addTask(title: String, desc: String, date: String, time: String) {
        val uid = auth.currentUser?.uid ?: return

        val formattedDate = try {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)
                ?.let { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it) }
        } catch (e: Exception) {
            date // jeśli coś pójdzie nie tak, zapisuje oryginalną wartość
        }

        val task = Tasks(title = title, description = desc, date = formattedDate ?: date, time = time, userId = uid)
        val doc = db.collection("tasks").document()
        task.id = doc.id
        doc.set(task).addOnSuccessListener {
            loadTasks()
        }
    }


    fun loadTasks() {
        val uid = auth.currentUser?.uid ?: return

        db.collection("tasks")
            .whereEqualTo("userId", uid)
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.mapNotNull { it.toObject(Tasks::class.java) }
                _tasks.value = list
            }
    }

    fun toggleTask(task: Tasks) {
        val updatedTask = task.copy(isCompleted = !task.isCompleted)
        db.collection("tasks").document(updatedTask.id).set(updatedTask)
        loadTasks()
    }


    fun deleteTask(task: Tasks) {
        db.collection("tasks").document(task.id).delete().addOnSuccessListener {
            loadTasks()
        }

    }




}
