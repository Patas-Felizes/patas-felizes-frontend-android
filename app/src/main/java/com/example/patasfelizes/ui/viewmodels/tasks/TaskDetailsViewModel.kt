package com.example.patasfelizes.ui.viewmodels.task

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Task
import com.example.patasfelizes.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class TaskDetailsState {
    object Loading : TaskDetailsState()
    data class Success(val task: Task, val isDeleting: Boolean = false) : TaskDetailsState()
    data class Error(val message: String) : TaskDetailsState()
}

class TaskDetailsViewModel : ViewModel() {
    private val repository = TaskRepository()
    private val _uiState = MutableStateFlow<TaskDetailsState>(TaskDetailsState.Loading)
    val uiState: StateFlow<TaskDetailsState> = _uiState.asStateFlow()

    fun loadTask(id: Int) {
        _uiState.value = TaskDetailsState.Loading
        repository.getTask(
            id = id,
            onSuccess = { task ->
                _uiState.value = TaskDetailsState.Success(task)
            },
            onError = { error ->
                _uiState.value = TaskDetailsState.Error(error)
            }
        )
    }

    fun deleteTask(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is TaskDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)

            repository.deleteTask(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = TaskDetailsState.Error(error)
                }
            )
        }
    }
}