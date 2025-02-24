package com.example.patasfelizes.ui.screens.procedures

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.components.*
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.ui.viewmodels.procedure.ProcedureListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProceduresScreen(
    navController: NavHostController,
    viewModel: ProcedureListViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val procedures by viewModel.procedures.collectAsState()

    val filterOptions = remember {
        listOf(
            FilterOption("Castração"),
            FilterOption("Cirurgia"),
            FilterOption("Vacinação"),
            FilterOption("Consulta"),
            FilterOption("Exame de Sangue"),
            FilterOption("Microchipagem")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    LaunchedEffect(Unit) {
        viewModel.reloadProcedures()
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addProcedure") },
                contentDescription = "Adicionar Procedimento"
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
                    placeholderText = "Pesquisar procedimento...",
                    onClearSearch = { searchQuery = TextFieldValue("") }
                )

                FilterComponent(
                    filterOptions = currentFilters,
                    onFilterChanged = { updatedFilters ->
                        currentFilters = updatedFilters
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ProcedureList(
                    procedures = procedures.filter {
                        (it.descricao.contains(searchQuery.text, ignoreCase = true) ||
                                it.tipo.contains(searchQuery.text, ignoreCase = true)) &&
                                (currentFilters.find { filter -> filter.isSelected && filter.name == it.tipo } != null ||
                                        currentFilters.none { filter -> filter.isSelected })
                    },
                    onProcedureClick = { procedure ->
                        navController.navigate("procedureDetails/${procedure.procedimento_id}")
                    }
                )
            }
        }
    }
}

@Composable
fun ProcedureList(
    procedures: List<Procedure>,
    onProcedureClick: (Procedure) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(procedures) { index, procedure ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            }
            ProcedureListItem(
                procedure = procedure,
                backgroundColor = backgroundColor,
                onClick = { onProcedureClick(procedure) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ProcedureListItem(
    procedure: Procedure,
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
                text = procedure.tipo,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = procedure.descricao,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Data: ${procedure.data_procedimento}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "R$ ${procedure.valor}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            procedure.animal_id?.let {
                Text(
                    text = "ID do Animal: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            procedure.voluntario_id?.let {
                Text(
                    text = "ID do Voluntário: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}