package com.example.patasfelizes.ui.screens.campaigns

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
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignsScreen(
    navController: NavHostController,
    viewModel: CampaignListViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val campaigns by viewModel.campaigns.collectAsState()

    val filterOptions = remember {
        listOf(
            FilterOption("Evento"),
            FilterOption("Financeira"),
            FilterOption("Saúde"),
            FilterOption("Alimentação"),
            FilterOption("Adoção")
        )
    }

    var currentFilters by remember { mutableStateOf(filterOptions) }

    LaunchedEffect(Unit) {
        viewModel.reloadCampaigns()
    }

    Scaffold(
        floatingActionButton = {
            CustomFloatingActionButton(
                onClick = { navController.navigate("addCampaign") },
                contentDescription = "Adicionar Campanha"
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
                    placeholderText = "Pesquisar campanha...",
                    onClearSearch = { searchQuery = TextFieldValue("") }
                )

                FilterComponent(
                    filterOptions = currentFilters,
                    onFilterChanged = { updatedFilters ->
                        currentFilters = updatedFilters
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                CampaignList(
                    campaigns = campaigns.filter {
                        it.nome.contains(searchQuery.text, ignoreCase = true) ||
                                it.tipo.contains(searchQuery.text, ignoreCase = true)
                    },
                    onCampaignClick = { campaign ->
                        navController.navigate("campaignDetails/${campaign.campanha_id}")
                    }
                )
            }
        }
    }
}

@Composable
fun CampaignList(
    campaigns: List<Campaign>,
    onCampaignClick: (Campaign) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(campaigns) { index, campaign ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            }
            CampaignListItem(
                campaign = campaign,
                backgroundColor = backgroundColor,
                onClick = { onCampaignClick(campaign) }
            )
        }
    }
}

@Composable
fun CampaignListItem(
    campaign: Campaign,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = campaign.nome,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = campaign.tipo,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Período: ${campaign.data_inicio} a ${campaign.data_termino}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}