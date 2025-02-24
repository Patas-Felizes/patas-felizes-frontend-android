package com.example.patasfelizes.ui.viewmodels.stock

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class StockDetailsState {
    object Loading : StockDetailsState()
    data class Success(val stock: Stock, val isDeleting: Boolean = false) : StockDetailsState()
    data class Error(val message: String) : StockDetailsState()
}

class StockDetailsViewModel : ViewModel() {
    private val repository = StockRepository()
    private val _uiState = MutableStateFlow<StockDetailsState>(StockDetailsState.Loading)
    val uiState: StateFlow<StockDetailsState> = _uiState.asStateFlow()

    fun loadStock(id: Int) {
        _uiState.value = StockDetailsState.Loading
        repository.getStock(
            id = id,
            onSuccess = { stock ->
                _uiState.value = StockDetailsState.Success(stock)
            },
            onError = { error ->
                _uiState.value = StockDetailsState.Error(error)
            }
        )
    }

    fun deleteStock(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is StockDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)

            repository.deleteStock(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = StockDetailsState.Error(error)
                }
            )
        }
    }
}