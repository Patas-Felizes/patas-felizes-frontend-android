package com.example.patasfelizes.ui.viewmodels.support

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Support
import com.example.patasfelizes.repository.SupportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SupportListViewModel : ViewModel() {
    private val repository = SupportRepository()
    private val _supports = MutableStateFlow<List<Support>>(emptyList())
    val supports: StateFlow<List<Support>> = _supports.asStateFlow()

    init {
        loadSupports()
    }

    fun reloadSupports() {
        loadSupports()
    }

    private fun loadSupports() {
        repository.listSupports(
            onSuccess = { supportList ->
                _supports.value = supportList
            },
            onError = { error ->
                Log.e("SupportListViewModel", "Error loading supports: $error")
                _supports.value = emptyList()
            }
        )
    }
}