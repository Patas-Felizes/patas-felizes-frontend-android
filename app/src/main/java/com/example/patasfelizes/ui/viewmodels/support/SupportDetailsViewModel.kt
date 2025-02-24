package com.example.patasfelizes.ui.viewmodels.support

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Support
import com.example.patasfelizes.repository.SupportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class SupportDetailsState {
    object Loading : SupportDetailsState()
    data class Success(val support: Support, val isDeleting: Boolean = false) : SupportDetailsState()
    data class Error(val message: String) : SupportDetailsState()
}

class SupportDetailsViewModel : ViewModel() {
    private val repository = SupportRepository()
    private val _uiState = MutableStateFlow<SupportDetailsState>(SupportDetailsState.Loading)
    val uiState: StateFlow<SupportDetailsState> = _uiState.asStateFlow()

    fun loadSupport(id: Int) {
        _uiState.value = SupportDetailsState.Loading
        repository.getSupport(
            id = id,
            onSuccess = { support ->
                _uiState.value = SupportDetailsState.Success(support)
            },
            onError = { error ->
                _uiState.value = SupportDetailsState.Error(error)
            }
        )
    }

    fun deleteSupport(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is SupportDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)

            repository.deleteSupport(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = SupportDetailsState.Error(error)
                }
            )
        }
    }
}