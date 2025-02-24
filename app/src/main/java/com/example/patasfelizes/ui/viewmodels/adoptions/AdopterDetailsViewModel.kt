package com.example.patasfelizes.ui.viewmodels.adopters

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.repository.AdoptersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AdopterDetailsState {
    object Loading : AdopterDetailsState()
    data class Success(val adopter: Adopter, val isDeleting: Boolean = false) : AdopterDetailsState()
    data class Error(val message: String) : AdopterDetailsState()
}

class AdopterDetailsViewModel : ViewModel() {
    private val repository = AdoptersRepository()
    private val _uiState = MutableStateFlow<AdopterDetailsState>(AdopterDetailsState.Loading)
    val uiState: StateFlow<AdopterDetailsState> = _uiState.asStateFlow()

    fun loadAdopter(id: Int) {
        _uiState.value = AdopterDetailsState.Loading
        repository.getAdopter(
            id = id,
            onSuccess = { adopter ->
                _uiState.value = AdopterDetailsState.Success(adopter)
            },
            onError = { error ->
                _uiState.value = AdopterDetailsState.Error(error)
            }
        )
    }

    fun deleteAdopter(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is AdopterDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)
            repository.deleteAdopter(
                id = id,
                onSuccess = { onComplete() },
                onError = { error ->
                    _uiState.value = AdopterDetailsState.Error(error)
                }
            )
        }
    }
}
