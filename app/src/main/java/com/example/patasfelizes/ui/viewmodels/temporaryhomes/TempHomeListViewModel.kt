package com.example.patasfelizes.ui.viewmodels.temphomes

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.TempHome
import com.example.patasfelizes.repository.TempHomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TempHomeListViewModel : ViewModel() {
    private val repository = TempHomeRepository()
    private val _tempHomes = MutableStateFlow<List<TempHome>>(emptyList())
    val tempHomes: StateFlow<List<TempHome>> = _tempHomes.asStateFlow()

    init {
        loadTempHomes()
    }

    private fun loadTempHomes() {
        repository.listTempHomes(
            onSuccess = { tempHomeList ->
                _tempHomes.value = tempHomeList
            },
            onError = { error ->
                Log.e("TempHomeListViewModel", "Error loading temporary homes: $error")
                _tempHomes.value = emptyList()
            }
        )
    }

    fun reloadTempHomes() {
        loadTempHomes()
    }
}
