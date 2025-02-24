package com.example.patasfelizes.ui.viewmodels.adoptions

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Adoption
import com.example.patasfelizes.repository.AdoptionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AdoptionDetailsState {
    object Loading : AdoptionDetailsState()
    data class Success(val adoption: Adoption, val isDeleting: Boolean = false) : AdoptionDetailsState()
    data class Error(val message: String) : AdoptionDetailsState()
}

class AdoptionDetailsViewModel : ViewModel() {
    private val repository = AdoptionsRepository()
    private val _uiState = MutableStateFlow<AdoptionDetailsState>(AdoptionDetailsState.Loading)
    val uiState: StateFlow<AdoptionDetailsState> = _uiState.asStateFlow()

    fun loadAdoption(id: Int) {
        _uiState.value = AdoptionDetailsState.Loading
        repository.getAdoption(
            id = id,
            onSuccess = { adoption ->
                _uiState.value = AdoptionDetailsState.Success(adoption)
            },
            onError = { error ->
                _uiState.value = AdoptionDetailsState.Error(error)
            }
        )
    }

    fun deleteAdoption(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is AdoptionDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)
            repository.deleteAdoption(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = AdoptionDetailsState.Error(error)
                }
            )
        }
    }
}
