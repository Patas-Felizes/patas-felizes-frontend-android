package com.example.patasfelizes.ui.screens.finances

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.*

// Tela Finanças
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    // editar filtros conforme cada tela
    val filterOptions = remember {
        listOf(
            FilterOption("Para adoção"),
            FilterOption("Adotado"),
            FilterOption("Em lar temporário"),
            FilterOption("Em tratamento")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = {
                    val route = if (selectedTabIndex == 0) "addExpense" else "addDonation"
                    navController.navigate(route)
                },
                contentDescription = if (selectedTabIndex == 0) "Adicionar Despesa" else "Adicionar Doação"
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
                    placeholderText = if (selectedTabIndex == 0) "Pesquisar..." else "Pesquisar...",
                    onClearSearch = { searchQuery = TextFieldValue("") }
                )

                ToggleSwitch(
                    options = listOf("Despesas", "Doações"),
                    selectedOptionIndex = selectedTabIndex,
                    onOptionSelected = { selectedTabIndex = it }
                )

                FilterComponent(
                    filterOptions = currentFilters,
                    onFilterChanged = { updatedFilters ->
                        currentFilters = updatedFilters
                    }
                )

                // Conteúdo baseado na aba selecionada
                if (selectedTabIndex == 0) {
                    // ExpensesContent(searchQuery.text)
                } else {
                    // DonationsContent(searchQuery.text)
                }
            }
        }
    }
}

