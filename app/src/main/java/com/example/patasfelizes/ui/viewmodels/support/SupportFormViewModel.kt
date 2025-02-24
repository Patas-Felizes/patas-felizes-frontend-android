package com.example.patasfelizes.ui.viewmodels.support

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Support
import com.example.patasfelizes.repository.SupportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class SupportFormState {
    object Idle : SupportFormState()
    object Loading : SupportFormState()
    data class Success(val support: Support) : SupportFormState()
    data class Error(val message: String) : SupportFormState()
}

class SupportFormViewModel : ViewModel() {
    private val repository = SupportRepository()
    private val _state = MutableStateFlow<SupportFormState>(SupportFormState.Idle)
    val state: StateFlow<SupportFormState> = _state.asStateFlow()

    fun createSupport(support: Support, onComplete: () -> Unit) {
        _state.value = SupportFormState.Loading
        repository.createSupport(
            support = support,
            onSuccess = { createdSupport ->
                _state.value = SupportFormState.Success(createdSupport)
                onComplete()
            },
            onError = { error ->
                _state.value = SupportFormState.Error(error)
            }
        )
    }

    fun updateSupport(support: Support, onComplete: () -> Unit) {
        _state.value = SupportFormState.Loading
        repository.updateSupport(
            id = support.apadrinhamento_id,
            support = support,
            onSuccess = { updatedSupport ->
                _state.value = SupportFormState.Success(updatedSupport)
                onComplete()
            },
            onError = { error ->
                _state.value = SupportFormState.Error(error)
            }
        )
    }
}