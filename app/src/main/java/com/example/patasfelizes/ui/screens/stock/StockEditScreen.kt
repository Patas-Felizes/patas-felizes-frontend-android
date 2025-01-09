package com.example.patasfelizes.ui.screens.stock

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Stock

@Composable
fun StockEditScreen(
    navController: NavHostController,
    stock: Stock,
    onSave: (String, String, String, String) -> Unit
) {
    StockFormScreen(
        initialCategory = stock.categoria,
        initialType = stock.tipoItem,
        initialAnimalSpecies = stock.animalEspecie,
        initialQuantity = stock.quantidade,
        onSave = { category, type, animalSpecies, quantity ->
            onSave(category, type, animalSpecies, quantity)
            navController.navigateUp()
        },
        onCancel = { navController.navigateUp() }
    )
}
