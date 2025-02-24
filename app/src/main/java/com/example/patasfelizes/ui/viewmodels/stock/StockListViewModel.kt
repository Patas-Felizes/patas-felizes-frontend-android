package com.example.patasfelizes.ui.viewmodels.stock

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class StockListViewModel : ViewModel() {
    private val repository = StockRepository()
    private val _stocks = MutableStateFlow<List<Stock>>(emptyList())
    val stocks: StateFlow<List<Stock>> = _stocks.asStateFlow()

    init {
        loadStocks()
    }

    fun reloadStocks() {
        loadStocks()
    }

    private fun loadStocks() {
        repository.listStock(
            onSuccess = { stockList ->
                _stocks.value = stockList
            },
            onError = { error ->
                Log.e("StockListViewModel", "Error loading stock items: $error")
                _stocks.value = emptyList()
            }
        )
    }
}