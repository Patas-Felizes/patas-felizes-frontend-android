package com.example.patasfelizes.ui.viewmodels.extense

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.repository.ExtenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class ExtenseFormState {
    object Idle : ExtenseFormState()
    object Loading : ExtenseFormState()
    data class Success(val extense: Extense) : ExtenseFormState()
    data class Error(val message: String) : ExtenseFormState()
}

class ExtenseFormViewModel : ViewModel() {
    private val repository = ExtenseRepository()
    private val _state = MutableStateFlow<ExtenseFormState>(ExtenseFormState.Idle)
    val state: StateFlow<ExtenseFormState> = _state.asStateFlow()

    fun createExtense(extense: Extense, onComplete: () -> Unit) {
        _state.value = ExtenseFormState.Loading
        repository.createExtense(
            extense = extense,
            onSuccess = { createdExtense ->
                _state.value = ExtenseFormState.Success(createdExtense)
                onComplete()
            },
            onError = { error ->
                _state.value = ExtenseFormState.Error(error)
            }
        )
    }

    fun updateExtense(extense: Extense, onComplete: () -> Unit) {
        _state.value = ExtenseFormState.Loading
        repository.updateExtense(
            id = extense.despesa_id,
            extense = extense,
            onSuccess = { updatedExtense ->
                _state.value = ExtenseFormState.Success(updatedExtense)
                onComplete()
            },
            onError = { error ->
                _state.value = ExtenseFormState.Error(error)
            }
        )
    }
}