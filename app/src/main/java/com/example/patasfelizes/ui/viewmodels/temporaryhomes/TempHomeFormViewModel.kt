package com.example.patasfelizes.ui.viewmodels.temphomes

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.repository.TempHomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class TempHomeFormState {
    object Idle : TempHomeFormState()
    object Loading : TempHomeFormState()
    data class Success(val tempHome: TempHome) : TempHomeFormState()
    data class Error(val message: String) : TempHomeFormState()
}

class TempHomeFormViewModel : ViewModel() {
    private val repository = TempHomeRepository()
    private val _state = MutableStateFlow<TempHomeFormState>(TempHomeFormState.Idle)
    val state: StateFlow<TempHomeFormState> = _state.asStateFlow()

    fun createTempHome(tempHome: TempHome, onComplete: () -> Unit) {
        _state.value = TempHomeFormState.Loading
        repository.createTempHome(
            tempHome = tempHome,
            onSuccess = { createdTempHome ->
                _state.value = TempHomeFormState.Success(createdTempHome)
                onComplete()
            },
            onError = { error ->
                _state.value = TempHomeFormState.Error(error)
            }
        )
    }

    fun updateTempHome(tempHome: TempHome, onComplete: () -> Unit) {
        _state.value = TempHomeFormState.Loading
        repository.updateTempHome(
            id = tempHome.lar_temporario_id,
            tempHome = tempHome,
            onSuccess = { updatedTempHome ->
                _state.value = TempHomeFormState.Success(updatedTempHome)
                onComplete()
            },
            onError = { error ->
                _state.value = TempHomeFormState.Error(error)
            }
        )
    }
}
