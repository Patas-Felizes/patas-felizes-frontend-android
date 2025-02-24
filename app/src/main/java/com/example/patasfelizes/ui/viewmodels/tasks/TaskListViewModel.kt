package com.example.patasfelizes.ui.viewmodels.task

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskListViewModel : ViewModel() {
    private val repository = TaskRepository()
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        loadTasks()
    }

    fun reloadTasks() {
        loadTasks()
    }

    private fun loadTasks() {
        repository.listTasks(
            onSuccess = { taskList ->
                _tasks.value = taskList
            },
            onError = { error ->
                Log.e("TaskListViewModel", "Error loading tasks: $error")
                _tasks.value = emptyList()
            }
        )
    }
}