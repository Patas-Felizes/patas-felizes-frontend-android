package com.example.patasfelizes.ui.viewmodels.team

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.repository.VoluntaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TeamListViewModel : ViewModel() {
    private val repository = VoluntaryRepository()
    private val _voluntarios = MutableStateFlow<List<Voluntary>>(emptyList())
    val voluntarios: StateFlow<List<Voluntary>> = _voluntarios.asStateFlow()

    init {
        loadVoluntarios()
    }

    fun reloadVoluntarios() {
        loadVoluntarios()
    }

    private fun loadVoluntarios() {
        repository.listVoluntarios(
            onSuccess = { voluntariosList ->
                _voluntarios.value = voluntariosList
            },
            onError = { error ->
                Log.e("TeamListViewModel", "Error loading voluntarios: $error")
                _voluntarios.value = emptyList()
            }
        )
    }
}