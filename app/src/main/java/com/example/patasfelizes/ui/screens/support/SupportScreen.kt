package com.example.patasfelizes.ui.screens.support

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
import com.example.patasfelizes.ui.components.*
import com.example.patasfelizes.models.Support
import com.example.patasfelizes.ui.viewmodels.support.SupportListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(
    navController: NavHostController,
    viewModel: SupportListViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val supports by viewModel.supports.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reloadSupports()
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addSupport") },
                contentDescription = "Adicionar Apadrinhamento"
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

                Spacer(modifier = Modifier.height(8.dp))

                SupportList(
                    supports = supports.filter {
                        it.nome_apadrinhador.contains(searchQuery.text, ignoreCase = true)
                    },
                    onSupportClick = { support ->
                        navController.navigate("supportDetails/${support.apadrinhamento_id}")
                    }
                )
            }
        }
    }
}

@Composable
fun SupportList(
    supports: List<Support>,
    onSupportClick: (Support) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(supports) { index, support ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            }
            SupportListItem(
                support = support,
                backgroundColor = backgroundColor,
                onClick = { onSupportClick(support) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SupportListItem(
    support: Support,
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
                text = "ID do Animal: ${support.animal_id}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Padrinho: ${support.nome_apadrinhador}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Valor: R$ ${support.valor}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Data: ${support.data_cadastro}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}