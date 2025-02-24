package com.example.patasfelizes.ui.viewmodels.team

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.repository.VoluntaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class TeamDetailsState {
    object Loading : TeamDetailsState()
    data class Success(val voluntario: Voluntary, val isDeleting: Boolean = false) : TeamDetailsState()
    data class Error(val message: String) : TeamDetailsState()
}

class TeamDetailsViewModel : ViewModel() {
    private val repository = VoluntaryRepository()
    private val _uiState = MutableStateFlow<TeamDetailsState>(TeamDetailsState.Loading)
    val uiState: StateFlow<TeamDetailsState> = _uiState.asStateFlow()

    fun loadVoluntario(id: Int) {
        _uiState.value = TeamDetailsState.Loading
        repository.getVoluntario(
            id = id,
            onSuccess = { voluntario ->
                _uiState.value = TeamDetailsState.Success(voluntario)
            },
            onError = { error ->
                _uiState.value = TeamDetailsState.Error(error)
            }
        )
    }

    fun deleteVoluntario(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is TeamDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)

            repository.deleteVoluntario(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = TeamDetailsState.Error(error)
                }
            )
        }
    }
}