package com.example.patasfelizes.ui.screens.stock

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.ui.viewmodels.stock.StockFormState
import com.example.patasfelizes.ui.viewmodels.stock.StockFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockEditScreen(
    navController: NavHostController,
    stock: Stock,
    viewModel: StockFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is StockFormState.Error -> {
                val errorMessage = (state as StockFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

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