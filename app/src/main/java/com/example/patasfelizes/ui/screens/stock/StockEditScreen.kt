package com.example.patasfelizes.ui.screens.stock

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.ui.viewmodels.stock.StockFormState
import com.example.patasfelizes.ui.viewmodels.stock.StockFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockEditScreen(
    navController: NavHostController,
    stockId: Int,
    viewModel: StockFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val (stockLoaded, setStockLoaded) = remember { mutableStateOf<Stock?>(null) }

    // Carregar o estoque quando a tela for inicializada
    LaunchedEffect(stockId) {
        viewModel.loadStock(stockId) { stock ->
            setStockLoaded(stock)
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is StockFormState.Error -> {
                val errorMessage = (state as StockFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // Aguardar o estoque ser carregado antes de mostrar o formulário
    stockLoaded?.let { stock ->
        StockFormScreen(
            navController = navController,
            initialStock = stock,
            onSave = { updatedStock ->
                viewModel.updateStock(updatedStock) {
                    navController.navigateUp()
                }
            },
            isEditMode = true
        )
    }
}