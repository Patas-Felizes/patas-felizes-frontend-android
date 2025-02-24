package com.example.patasfelizes.ui.viewmodels.temphomes

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.repository.TempHomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class TempHomeDetailsState {
    object Loading : TempHomeDetailsState()
    data class Success(val tempHome: TempHome, val isDeleting: Boolean = false) : TempHomeDetailsState()
    data class Error(val message: String) : TempHomeDetailsState()
}

class TempHomeDetailsViewModel : ViewModel() {
    private val repository = TempHomeRepository()
    private val _uiState = MutableStateFlow<TempHomeDetailsState>(TempHomeDetailsState.Loading)
    val uiState: StateFlow<TempHomeDetailsState> = _uiState.asStateFlow()

    fun loadTempHome(id: Int) {
        _uiState.value = TempHomeDetailsState.Loading
        repository.getTempHome(
            id = id,
            onSuccess = { tempHome ->
                _uiState.value = TempHomeDetailsState.Success(tempHome)
            },
            onError = { error ->
                _uiState.value = TempHomeDetailsState.Error(error)
            }
        )
    }

    fun deleteTempHome(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is TempHomeDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)
            repository.deleteTempHome(
                id = id,
                onSuccess = { onComplete() },
                onError = { error ->
                    _uiState.value = TempHomeDetailsState.Error(error)
                }
            )
        }
    }
}
