package com.example.patasfelizes.ui.viewmodels.team

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.repository.VoluntaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class TeamFormState {
    object Idle : TeamFormState()
    object Loading : TeamFormState()
    data class Success(val voluntario: Voluntary) : TeamFormState()
    data class Error(val message: String) : TeamFormState()
}

class TeamFormViewModel : ViewModel() {
    private val repository = VoluntaryRepository()
    private val _state = MutableStateFlow<TeamFormState>(TeamFormState.Idle)
    val state: StateFlow<TeamFormState> = _state.asStateFlow()

    fun createVoluntario(voluntario: Voluntary, onComplete: () -> Unit) {
        _state.value = TeamFormState.Loading
        repository.createVoluntario(
            voluntario = voluntario,
            onSuccess = { createdVoluntario ->
                _state.value = TeamFormState.Success(createdVoluntario)
                onComplete()
            },
            onError = { error ->
                _state.value = TeamFormState.Error(error)
            }
        )
    }

    fun updateVoluntario(voluntario: Voluntary, onComplete: () -> Unit) {
        _state.value = TeamFormState.Loading
        repository.updateVoluntario(
            id = voluntario.voluntario_id,
            voluntario = voluntario,
            onSuccess = { updatedVoluntario ->
                _state.value = TeamFormState.Success(updatedVoluntario)
                onComplete()
            },
            onError = { error ->
                _state.value = TeamFormState.Error(error)
            }
        )
    }
}