package com.example.patasfelizes.ui.viewmodels.adopters

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.repository.AdoptersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AdopterFormState {
    object Idle : AdopterFormState()
    object Loading : AdopterFormState()
    data class Success(val adopter: Adopter) : AdopterFormState()
    data class Error(val message: String) : AdopterFormState()
}

class AdopterFormViewModel : ViewModel() {
    private val repository = AdoptersRepository()
    private val _state = MutableStateFlow<AdopterFormState>(AdopterFormState.Idle)
    val state: StateFlow<AdopterFormState> = _state.asStateFlow()

    fun createAdopter(adopter: Adopter, onComplete: () -> Unit) {
        _state.value = AdopterFormState.Loading
        repository.createAdopter(
            adopter = adopter,
            onSuccess = { createdAdopter ->
                _state.value = AdopterFormState.Success(createdAdopter)
                onComplete()
            },
            onError = { error ->
                _state.value = AdopterFormState.Error(error)
            }
        )
    }

    fun updateAdopter(adopter: Adopter, onComplete: () -> Unit) {
        _state.value = AdopterFormState.Loading
        repository.updateAdopter(
            id = adopter.adotante_id,
            adopter = adopter,
            onSuccess = { updatedAdopter ->
                _state.value = AdopterFormState.Success(updatedAdopter)
                onComplete()
            },
            onError = { error ->
                _state.value = AdopterFormState.Error(error)
            }
        )
    }
}
