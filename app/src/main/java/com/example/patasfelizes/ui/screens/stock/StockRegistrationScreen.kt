package com.example.patasfelizes.ui.screens.stock

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Stock

@Composable
fun StockRegistrationScreen(
    navController: NavHostController,
    onSave: (Stock) -> Unit
) {
    StockFormScreen(
        navController = navController,
        initialStock = null,
        onSave = { stock ->
            onSave(stock)
            navController.navigateUp()
        },
        isEditMode = false
    )
}
