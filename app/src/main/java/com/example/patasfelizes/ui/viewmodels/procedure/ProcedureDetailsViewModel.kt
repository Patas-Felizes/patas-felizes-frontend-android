package com.example.patasfelizes.ui.viewmodels.procedure

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.repository.ProcedureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class ProcedureDetailsState {
    object Loading : ProcedureDetailsState()
    data class Success(val procedure: Procedure, val isDeleting: Boolean = false) : ProcedureDetailsState()
    data class Error(val message: String) : ProcedureDetailsState()
}

class ProcedureDetailsViewModel : ViewModel() {
    private val repository = ProcedureRepository()
    private val _uiState = MutableStateFlow<ProcedureDetailsState>(ProcedureDetailsState.Loading)
    val uiState: StateFlow<ProcedureDetailsState> = _uiState.asStateFlow()

    fun loadProcedure(id: Int) {
        _uiState.value = ProcedureDetailsState.Loading
        repository.getProcedure(
            id = id,
            onSuccess = { procedure ->
                _uiState.value = ProcedureDetailsState.Success(procedure)
            },
            onError = { error ->
                _uiState.value = ProcedureDetailsState.Error(error)
            }
        )
    }

    fun deleteProcedure(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is ProcedureDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)
            repository.deleteProcedure(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = ProcedureDetailsState.Error(error)
                }
            )
        }
    }
}
