package com.example.patasfelizes.ui.viewmodels.hosts

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Host
import com.example.patasfelizes.repository.HostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HostListViewModel : ViewModel() {
    private val repository = HostRepository()
    private val _hosts = MutableStateFlow<List<Host>>(emptyList())
    val hosts: StateFlow<List<Host>> = _hosts.asStateFlow()

    init {
        loadHosts()
    }

    private fun loadHosts() {
        repository.listHosts(
            onSuccess = { hostList ->
                _hosts.value = hostList
            },
            onError = { error ->
                Log.e("HostListViewModel", "Error loading hosts: $error")
                _hosts.value = emptyList()
            }
        )
    }

    fun reloadHosts() {
        loadHosts()
    }
}
