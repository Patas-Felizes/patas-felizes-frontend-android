package com.example.patasfelizes.ui.viewmodels.animals

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.repository.AnimalsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnimalListViewModel : ViewModel() {
    private val repository = AnimalsRepository()
    private val _animals = MutableStateFlow<List<Animal>>(emptyList())
    val animals: StateFlow<List<Animal>> = _animals.asStateFlow()

    init {
        loadAnimals()
    }

    fun reloadAnimals() {
        loadAnimals()
    }

    private fun loadAnimals() {
        repository.listAnimals(
            onSuccess = { animalList ->
                _animals.value = animalList
            },
            onError = { error ->
                Log.e("AnimalListViewModel", "Error loading animals: $error")
                _animals.value = emptyList()
            }
        )
    }
}