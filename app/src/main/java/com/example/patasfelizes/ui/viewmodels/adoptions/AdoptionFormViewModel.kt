package com.example.patasfelizes.ui.viewmodels.adoptions

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Adoption
import com.example.patasfelizes.repository.AdoptionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AdoptionFormState {
    object Idle : AdoptionFormState()
    object Loading : AdoptionFormState()
    data class Success(val adoption: Adoption) : AdoptionFormState()
    data class Error(val message: String) : AdoptionFormState()
}

class AdoptionFormViewModel : ViewModel() {
    private val repository = AdoptionsRepository()
    private val _state = MutableStateFlow<AdoptionFormState>(AdoptionFormState.Idle)
    val state: StateFlow<AdoptionFormState> = _state.asStateFlow()

    fun loadAdoption(id: Int, onSuccess: (Adoption) -> Unit) {
        repository.getAdoption(
            id = id,
            onSuccess = { adoption ->
                onSuccess(adoption)
            },
            onError = { error ->
                _state.value = AdoptionFormState.Error(error)
            }
        )
    }

    fun createAdoption(adoption: Adoption, onComplete: () -> Unit) {
        _state.value = AdoptionFormState.Loading
        repository.createAdoption(
            adoption = adoption,
            onSuccess = { createdAdoption ->
                _state.value = AdoptionFormState.Success(createdAdoption)
                onComplete()
            },
            onError = { error ->
                _state.value = AdoptionFormState.Error(error)
            }
        )
    }

    fun updateAdoption(id: Int, adoption: Adoption, onComplete: () -> Unit) {
        _state.value = AdoptionFormState.Loading
        repository.updateAdoption(
            id = id,
            adoption = adoption,
            onSuccess = { updatedAdoption ->
                _state.value = AdoptionFormState.Success(updatedAdoption)
                onComplete()
            },
            onError = { error ->
                _state.value = AdoptionFormState.Error(error)
            }
        )
    }
}
