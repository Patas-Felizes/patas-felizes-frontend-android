package com.example.patasfelizes.ui.screens.stock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Stock
import com.example.patasfelizes.models.StockList
import com.example.patasfelizes.ui.components.CustomFloatingActionButton
import com.example.patasfelizes.ui.components.CustomSearchBar
import com.example.patasfelizes.ui.components.FilterComponent
import com.example.patasfelizes.ui.components.FilterOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Filtros existentes
    val filterOptions = remember {
        listOf(
            FilterOption("Alimentação"),
            FilterOption("Saúde"),
            FilterOption("Higiene")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addStock") },
                contentDescription = "Adicionar Estoque"
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                CustomSearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                    placeholderText = "Pesquisar...",
                    onClearSearch = { searchQuery = TextFieldValue("") }
                )

                FilterComponent(
                    filterOptions = currentFilters,
                    onFilterChanged = { updatedFilters ->
                        currentFilters = updatedFilters
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                StockListContent(
                    stockItems = StockList.filter {
                        it.tipoItem.contains(searchQuery.text, ignoreCase = true) ||
                                it.animalEspecie.contains(searchQuery.text, ignoreCase = true)
                    },
                    onStockClick = { stock ->
                        navController.navigate("stockDetails/${stock.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun StockListContent(
    stockItems: List<Stock>,
    onStockClick: (Stock) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(stockItems) { index, stock ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary // Cor 1
            } else {
                MaterialTheme.colorScheme.background // Cor 2
            }
            StockListItem(
                stock = stock,
                backgroundColor = backgroundColor,
                onClick = { onStockClick(stock) }
            )
        }
    }
}

@Composable
fun StockListItem(
    stock: Stock,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stock.tipoItem,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Espécie: ${stock.animalEspecie}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Quantidade: ${stock.quantidade}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
