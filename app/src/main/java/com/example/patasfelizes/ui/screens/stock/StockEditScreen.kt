package com.example.patasfelizes.ui.screens.stock

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Stock

@Composable
fun StockEditScreen(
    navController: NavHostController,
    stock: Stock,
    onSave: (Stock) -> Unit
) {
    StockFormScreen(
        navController = navController,
        initialStock = stock,
        onSave = { updatedStock ->
            onSave(updatedStock)
            navController.navigateUp()
        },
        isEditMode = true
    )
}
