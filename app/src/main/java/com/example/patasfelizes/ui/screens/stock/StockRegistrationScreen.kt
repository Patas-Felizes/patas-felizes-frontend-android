package com.example.patasfelizes.ui.screens.stock

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.stock.StockFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockRegistrationScreen(
    navController: NavHostController,
    viewModel: StockFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    StockFormScreen(
        navController = navController,
        onSave = { stock ->
            viewModel.createStock(stock) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}