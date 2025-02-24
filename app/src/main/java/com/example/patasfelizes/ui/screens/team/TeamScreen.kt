package com.example.patasfelizes.ui.screens.team

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.patasfelizes.R
import com.example.patasfelizes.ui.components.*
import com.example.patasfelizes.ui.viewmodels.team.TeamListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen(
    navController: NavHostController,
    viewModel: TeamListViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val voluntarios by viewModel.voluntarios.collectAsState()

    LaunchedEffect(true) {
        viewModel.reloadVoluntarios()
    }

    val filteredVolunteers = voluntarios.filter { voluntary ->
        searchQuery.text.isEmpty() ||
                voluntary.nome.contains(searchQuery.text, ignoreCase = true) ||
                voluntary.email.contains(searchQuery.text, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addVoluntary") },
                contentDescription = "Adicionar Membro da Equipe"
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

                LazyColumn(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(filteredVolunteers) { voluntary ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 6.dp)
                                    .clickable {
                                        navController.navigate("voluntaryDetails/${voluntary.voluntario_id}")
                                    }
                                    .fillMaxWidth(0.9f),
                                elevation = CardDefaults.cardElevation(3.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    AsyncImage(
                                        model = voluntary.foto,
                                        contentDescription = voluntary.nome,
                                        modifier = Modifier
                                            .size(130.dp)
                                            .clip(RoundedCornerShape(topEnd = 0.dp, bottomEnd = 0.dp))
                                            .aspectRatio(1f),
                                        contentScale = ContentScale.Crop,
                                        error = painterResource(id = R.drawable.default_image)
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(top = 9.dp),
                                        verticalArrangement = Arrangement.Top
                                    ) {
                                        Text(
                                            text = voluntary.nome,
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onTertiary,
                                            modifier = Modifier.padding(bottom = 2.dp)
                                        )

                                        Text(
                                            text = voluntary.email,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                        Text(
                                            text = voluntary.telefone,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}