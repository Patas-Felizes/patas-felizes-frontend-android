package com.example.patasfelizes.ui.viewmodels.animals

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.repository.AnimalsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AnimalFormState {
    object Idle : AnimalFormState()
    object Loading : AnimalFormState()
    data class Success(val animal: Animal) : AnimalFormState()
    data class Error(val message: String) : AnimalFormState()
}

class AnimalFormViewModel : ViewModel() {
    private val repository = AnimalsRepository()
    private val _state = MutableStateFlow<AnimalFormState>(AnimalFormState.Idle)
    val state: StateFlow<AnimalFormState> = _state.asStateFlow()

    fun createAnimal(animal: Animal, onComplete: () -> Unit) {
        _state.value = AnimalFormState.Loading
        repository.createAnimal(
            animal = animal,
            onSuccess = { createdAnimal ->
                _state.value = AnimalFormState.Success(createdAnimal)
                onComplete()
            },
            onError = { error ->
                _state.value = AnimalFormState.Error(error)
            }
        )
    }

    fun updateAnimal(animal: Animal, onComplete: () -> Unit) {
        _state.value = AnimalFormState.Loading
        repository.updateAnimal(
            id = animal.animal_id,
            animal = animal,
            onSuccess = { updatedAnimal ->
                _state.value = AnimalFormState.Success(updatedAnimal)
                onComplete()
            },
            onError = { error ->
                _state.value = AnimalFormState.Error(error)
            }
        )
    }
}