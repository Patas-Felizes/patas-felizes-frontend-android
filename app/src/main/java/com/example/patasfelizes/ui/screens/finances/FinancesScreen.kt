package com.example.patasfelizes.ui.screens.finances

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.*
import com.example.patasfelizes.ui.screens.finances.donations.DonationsContent
import com.example.patasfelizes.ui.screens.finances.extenses.ExtensesContent
import com.example.patasfelizes.ui.viewmodels.extense.ExtenseListViewModel
import com.example.patasfelizes.ui.viewmodels.donation.DonationListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesScreen(
    navController: NavHostController,
    extenseViewModel: ExtenseListViewModel = viewModel(),
    donationViewModel: DonationListViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    val extenses by extenseViewModel.extenses.collectAsState()
    val donations by donationViewModel.donations.collectAsState()

    LaunchedEffect(Unit) {
        extenseViewModel.reloadExtenses()
        donationViewModel.reloadDonations()
    }

    // Filtros específicos para cada aba
    val filterOptions = remember {
        listOf(
            FilterOption("Veterinário"),
            FilterOption("Medicamentos"),
            FilterOption("Ração"),
            FilterOption("Outros")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    // Dados filtrados e buscados
    val filteredExpenses = extenses.filter {
        (searchQuery.text.isEmpty() ||
                it.tipo.contains(searchQuery.text, ignoreCase = true) ||
                it.valor.contains(searchQuery.text, ignoreCase = true))
    }

    val filteredDonations = donations.filter {
        (searchQuery.text.isEmpty() ||
                it.doador.contains(searchQuery.text, ignoreCase = true) ||
                it.valor.contains(searchQuery.text, ignoreCase = true))
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = {
                    val route = if (selectedTabIndex == 0) "addExtense" else "addDonation"
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
                    placeholderText = if (selectedTabIndex == 0) "Pesquisar despesas..." else "Pesquisar doações...",
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

                Spacer(modifier = Modifier.height(8.dp))

                // Listagem de conteúdo baseado na aba selecionada
//                if (selectedTabIndex == 0) {
//                    ExtensesContent(
//                        expenses = filteredExpenses,
//                        onExtenseClick = { expense ->
//                            navController.navigate("extenseDetails/${expense.despesa_id}")
//                        }
//                    )
//                } else {
//                    DonationsContent(
//                        donations = filteredDonations,
//                        onDonationClick = { donation ->
//                            navController.navigate("donationDetails/${donation.doacao_id}")
//                        }
//                    )
//                }
            }
        }
    }
}