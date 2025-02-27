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
import com.example.patasfelizes.ui.viewmodels.animals.AnimalListViewModel
import com.example.patasfelizes.ui.viewmodels.team.TeamListViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProceduresScreen(
    navController: NavHostController,
    viewModel: ProcedureListViewModel = viewModel(),
    animalViewModel: AnimalListViewModel = viewModel(),
    voluntaryViewModel: TeamListViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val procedures by viewModel.procedures.collectAsState()
    val animals by animalViewModel.animals.collectAsState()
    val voluntaries by voluntaryViewModel.voluntarios.collectAsState()

    // Estado para controlar a visibilidade da animação
    val isVisible = remember { mutableStateOf(false) }

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
        animalViewModel.reloadAnimals()
        voluntaryViewModel.reloadVoluntarios()

        // Ativar a visibilidade após carregar os dados
        isVisible.value = true
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

                // Envolvendo a lista de procedimentos em Box com AnimatedVisibility
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
                        ProcedureList(
                            procedures = procedures.filter {
                                (it.descricao.contains(searchQuery.text, ignoreCase = true) ||
                                        it.tipo.contains(searchQuery.text, ignoreCase = true)) &&
                                        (currentFilters.find { filter -> filter.isSelected && filter.name == it.tipo } != null ||
                                                currentFilters.none { filter -> filter.isSelected })
                            },
                            animals = animals,
                            voluntaries = voluntaries,
                            onProcedureClick = { procedure ->
                                navController.navigate("procedureDetails/${procedure.procedimento_id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProcedureList(
    procedures: List<Procedure>,
    animals: List<com.example.patasfelizes.models.Animal>,
    voluntaries: List<com.example.patasfelizes.models.Voluntary>,
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
            val animal = procedure.animal_id?.let { animalId ->
                animals.find { it.animal_id == animalId }
            }
            val voluntary = procedure.voluntario_id?.let { voluntarioId ->
                voluntaries.find { it.voluntario_id == voluntarioId }
            }
            ProcedureListItem(
                procedure = procedure,
                animalName = animal?.nome,
                voluntaryName = voluntary?.nome,
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
    animalName: String?,
    voluntaryName: String?,
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
            if (animalName != null) {
                Text(
                    text = "Animal: $animalName",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (voluntaryName != null) {
                Text(
                    text = "Voluntário: $voluntaryName",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}