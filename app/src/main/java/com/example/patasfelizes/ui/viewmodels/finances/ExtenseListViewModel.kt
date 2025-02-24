package com.example.patasfelizes.ui.viewmodels.extense

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.repository.ExtenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExtenseListViewModel : ViewModel() {
    private val repository = ExtenseRepository()
    private val _extenses = MutableStateFlow<List<Extense>>(emptyList())
    val extenses: StateFlow<List<Extense>> = _extenses.asStateFlow()

    init {
        loadExtenses()
    }

    fun reloadExtenses() {
        loadExtenses()
    }

    private fun loadExtenses() {
        repository.listExtenses(
            onSuccess = { extenseList ->
                _extenses.value = extenseList
            },
            onError = { error ->
                Log.e("ExtenseListViewModel", "Error loading expenses: $error")
                _extenses.value = emptyList()
            }
        )
    }
}