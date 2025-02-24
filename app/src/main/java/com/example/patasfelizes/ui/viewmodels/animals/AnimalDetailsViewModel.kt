package com.example.patasfelizes.ui.viewmodels.animals

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.repository.AnimalsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class AnimalDetailsState {
    object Loading : AnimalDetailsState()
    data class Success(val animal: Animal, val isDeleting: Boolean = false) : AnimalDetailsState()
    data class Error(val message: String) : AnimalDetailsState()
}

class AnimalDetailsViewModel : ViewModel() {
    private val repository = AnimalsRepository()
    private val _uiState = MutableStateFlow<AnimalDetailsState>(AnimalDetailsState.Loading)
    val uiState: StateFlow<AnimalDetailsState> = _uiState.asStateFlow()

    fun loadAnimal(id: Int) {
        _uiState.value = AnimalDetailsState.Loading
        repository.getAnimal(
            id = id,
            onSuccess = { animal ->
                _uiState.value = AnimalDetailsState.Success(animal)
            },
            onError = { error ->
                _uiState.value = AnimalDetailsState.Error(error)
            }
        )
    }

    fun deleteAnimal(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is AnimalDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)

            repository.deleteAnimal(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = AnimalDetailsState.Error(error)
                }
            )
        }
    }
}