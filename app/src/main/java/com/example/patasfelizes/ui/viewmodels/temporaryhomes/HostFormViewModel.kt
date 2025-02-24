package com.example.patasfelizes.ui.viewmodels.hosts

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Host
import com.example.patasfelizes.repository.HostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class HostFormState {
    object Idle : HostFormState()
    object Loading : HostFormState()
    data class Success(val host: Host) : HostFormState()
    data class Error(val message: String) : HostFormState()
}

class HostFormViewModel : ViewModel() {
    private val repository = HostRepository()
    private val _state = MutableStateFlow<HostFormState>(HostFormState.Idle)
    val state: StateFlow<HostFormState> = _state.asStateFlow()

    fun createHost(host: Host, onComplete: () -> Unit) {
        _state.value = HostFormState.Loading
        repository.createHost(
            host = host,
            onSuccess = { createdHost ->
                _state.value = HostFormState.Success(createdHost)
                onComplete()
            },
            onError = { error ->
                _state.value = HostFormState.Error(error)
            }
        )
    }

    fun updateHost(host: Host, onComplete: () -> Unit) {
        _state.value = HostFormState.Loading
        repository.updateHost(
            id = host.hospedeiro_id,
            host = host,
            onSuccess = { updatedHost ->
                _state.value = HostFormState.Success(updatedHost)
                onComplete()
            },
            onError = { error ->
                _state.value = HostFormState.Error(error)
            }
        )
    }
}
