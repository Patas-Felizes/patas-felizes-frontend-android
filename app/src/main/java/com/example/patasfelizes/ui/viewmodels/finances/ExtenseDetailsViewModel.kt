package com.example.patasfelizes.ui.viewmodels.extense

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.repository.ExtenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class ExtenseDetailsState {
    object Loading : ExtenseDetailsState()
    data class Success(val extense: Extense, val isDeleting: Boolean = false) : ExtenseDetailsState()
    data class Error(val message: String) : ExtenseDetailsState()
}

class ExtenseDetailsViewModel : ViewModel() {
    private val repository = ExtenseRepository()
    private val _uiState = MutableStateFlow<ExtenseDetailsState>(ExtenseDetailsState.Loading)
    val uiState: StateFlow<ExtenseDetailsState> = _uiState.asStateFlow()

    fun loadExtense(id: Int) {
        _uiState.value = ExtenseDetailsState.Loading
        repository.getExtense(
            id = id,
            onSuccess = { extense ->
                _uiState.value = ExtenseDetailsState.Success(extense)
            },
            onError = { error ->
                _uiState.value = ExtenseDetailsState.Error(error)
            }
        )
    }

    fun deleteExtense(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is ExtenseDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)

            repository.deleteExtense(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = ExtenseDetailsState.Error(error)
                }
            )
        }
    }
}