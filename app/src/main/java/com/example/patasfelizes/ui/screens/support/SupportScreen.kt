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
import com.example.patasfelizes.ui.components.*
import com.example.patasfelizes.models.Sponsor
import com.example.patasfelizes.models.SponsorList
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

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
                    sponsors = SponsorList.filter {
                        it.idAnimal?.nome?.contains(searchQuery.text, ignoreCase = true) == true ||
                                it.padrinhoNome.contains(searchQuery.text, ignoreCase = true)
                    },
                    onSponsorClick = { sponsor ->
                        navController.navigate("supportDetails/${sponsor.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun SupportList(
    sponsors: List<Sponsor>,
    onSponsorClick: (Sponsor) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(sponsors) { index, sponsor ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            }
            SupportListItem(
                sponsor = sponsor,
                backgroundColor = backgroundColor,
                onClick = { onSponsorClick(sponsor) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SupportListItem(
    sponsor: Sponsor,
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
                text = sponsor.idAnimal?.nome ?: "Pet não identificado",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Padrinho: ${sponsor.padrinhoNome}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Valor: R$ ${sponsor.valor}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Data: ${
                    sponsor.dataCadastro.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                }",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
