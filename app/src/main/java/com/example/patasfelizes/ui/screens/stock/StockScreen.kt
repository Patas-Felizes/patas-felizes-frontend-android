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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.components.CustomFloatingActionButton
import com.example.patasfelizes.ui.components.CustomSearchBar
import com.example.patasfelizes.ui.components.FilterComponent
import com.example.patasfelizes.ui.components.FilterOption
import com.example.patasfelizes.ui.viewmodels.stock.StockListViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    navController: NavHostController,
    viewModel: StockListViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val stocks by viewModel.stocks.collectAsState()

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

    val filterOptions = remember {
        listOf(
            FilterOption("Alimentação"),
            FilterOption("Saúde"),
            FilterOption("Higiene")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    val filteredStocks = stocks.filter {
        it.tipo_item.contains(searchQuery.text, ignoreCase = true) ||
                it.especie_animal.contains(searchQuery.text, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.reloadStocks()

        // Ativar a visibilidade após carregar os dados
        isVisible.value = true
    }

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

                // Envolvendo a lista com Box e AnimatedVisibility
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Usando qualificação completa para evitar conflitos de escopo
                    androidx.compose.animation.AnimatedVisibility(
                        visible = isVisible.value,
                        enter = fadeIn(animationSpec = tween(durationMillis = 600)),
                        exit = fadeOut()
                    ) {
                        StockListContent(
                            stockItems = filteredStocks,
                            onStockClick = { stock ->
                                navController.navigate("stockDetails/${stock.estoque_id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StockListContent(
    stockItems: List<com.example.patasfelizes.models.Stock>,
    onStockClick: (com.example.patasfelizes.models.Stock) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(stockItems) { index, stock ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
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
    stock: com.example.patasfelizes.models.Stock,
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
                text = stock.tipo_item,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Espécie: ${stock.especie_animal}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}