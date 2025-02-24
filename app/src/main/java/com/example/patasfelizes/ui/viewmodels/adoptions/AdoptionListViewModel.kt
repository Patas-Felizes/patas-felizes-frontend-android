package com.example.patasfelizes.ui.viewmodels.adoptions

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Adoption
import com.example.patasfelizes.repository.AdoptionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdoptionListViewModel : ViewModel() {
    private val repository = AdoptionsRepository()
    private val _adoptions = MutableStateFlow<List<Adoption>>(emptyList())
    val adoptions: StateFlow<List<Adoption>> = _adoptions.asStateFlow()

    init {
        loadAdoptions()
    }

    fun reloadAdoptions() {
        loadAdoptions()
    }

    private fun loadAdoptions() {
        repository.listAdoptions(
            onSuccess = { adoptionList ->
                _adoptions.value = adoptionList
            },
            onError = { error ->
                Log.e("AdoptionListViewModel", "Error loading adoptions: $error")
                _adoptions.value = emptyList()
            }
        )
    }
}