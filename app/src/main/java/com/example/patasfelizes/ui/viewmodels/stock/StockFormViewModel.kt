// StockFormViewModel.kt
package com.example.patasfelizes.ui.viewmodels.stock

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class StockFormState {
    object Idle : StockFormState()
    object Loading : StockFormState()
    data class Success(val stock: Stock) : StockFormState()
    data class Error(val message: String) : StockFormState()
}

class StockFormViewModel : ViewModel() {
    private val repository = StockRepository()
    private val _state = MutableStateFlow<StockFormState>(StockFormState.Idle)
    val state: StateFlow<StockFormState> = _state.asStateFlow()

    fun createStock(stock: Stock, onComplete: () -> Unit) {
        _state.value = StockFormState.Loading
        repository.createStock(
            stock = stock,
            onSuccess = { createdStock ->
                _state.value = StockFormState.Success(createdStock)
                onComplete()
            },
            onError = { error ->
                _state.value = StockFormState.Error(error)
            }
        )
    }

    fun updateStock(stock: Stock, onComplete: () -> Unit) {
        _state.value = StockFormState.Loading
        repository.updateStock(
            id = stock.estoque_id,
            stock = stock,
            onSuccess = { updatedStock ->
                _state.value = StockFormState.Success(updatedStock)
                onComplete()
            },
            onError = { error ->
                _state.value = StockFormState.Error(error)
            }
        )
    }
}