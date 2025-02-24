package com.example.patasfelizes.ui.viewmodels.task

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class TaskFormState {
    object Idle : TaskFormState()
    object Loading : TaskFormState()
    data class Success(val task: Task) : TaskFormState()
    data class Error(val message: String) : TaskFormState()
}

class TaskFormViewModel : ViewModel() {
    private val repository = TaskRepository()
    private val _state = MutableStateFlow<TaskFormState>(TaskFormState.Idle)
    val state: StateFlow<TaskFormState> = _state.asStateFlow()

    fun createTask(task: Task, onComplete: () -> Unit) {
        _state.value = TaskFormState.Loading
        repository.createTask(
            task = task,
            onSuccess = { createdTask ->
                _state.value = TaskFormState.Success(createdTask)
                onComplete()
            },
            onError = { error ->
                _state.value = TaskFormState.Error(error)
            }
        )
    }

    fun updateTask(task: Task, onComplete: () -> Unit) {
        _state.value = TaskFormState.Loading
        repository.updateTask(
            id = task.tarefa_id,
            task = task,
            onSuccess = { updatedTask ->
                _state.value = TaskFormState.Success(updatedTask)
                onComplete()
            },
            onError = { error ->
                _state.value = TaskFormState.Error(error)
            }
        )
    }
}