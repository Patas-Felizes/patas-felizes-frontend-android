package com.example.patasfelizes.ui.viewmodels.hosts

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Host
import com.example.patasfelizes.repository.HostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class HostDetailsState {
    object Loading : HostDetailsState()
    data class Success(val host: Host, val isDeleting: Boolean = false) : HostDetailsState()
    data class Error(val message: String) : HostDetailsState()
}

class HostDetailsViewModel : ViewModel() {
    private val repository = HostRepository()
    private val _uiState = MutableStateFlow<HostDetailsState>(HostDetailsState.Loading)
    val uiState: StateFlow<HostDetailsState> = _uiState.asStateFlow()

    fun loadHost(id: Int) {
        _uiState.value = HostDetailsState.Loading
        repository.getHost(
            id = id,
            onSuccess = { host ->
                _uiState.value = HostDetailsState.Success(host)
            },
            onError = { error ->
                _uiState.value = HostDetailsState.Error(error)
            }
        )
    }

    fun deleteHost(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is HostDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)
            repository.deleteHost(
                id = id,
                onSuccess = { onComplete() },
                onError = { error ->
                    _uiState.value = HostDetailsState.Error(error)
                }
            )
        }
    }
}
