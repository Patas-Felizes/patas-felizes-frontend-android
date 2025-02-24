package com.example.patasfelizes.ui.viewmodels.adopters

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.repository.AdoptersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdopterListViewModel : ViewModel() {
    private val repository = AdoptersRepository()
    private val _adopters = MutableStateFlow<List<Adopter>>(emptyList())
    val adopters: StateFlow<List<Adopter>> = _adopters.asStateFlow()

    init {
        loadAdopters()
    }

    private fun loadAdopters() {
        repository.listAdopters(
            onSuccess = { adopterList ->
                _adopters.value = adopterList
            },
            onError = { error ->
                Log.e("AdopterListViewModel", "Erro ao carregar adotantes: $error")
                _adopters.value = emptyList()
            }
        )
    }

    fun reloadAdopters() {
        loadAdopters()
    }
}
