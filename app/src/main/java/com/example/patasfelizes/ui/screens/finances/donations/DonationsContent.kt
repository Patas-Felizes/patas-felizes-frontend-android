package com.example.patasfelizes.ui.screens.finances.donations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.models.Donation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationsContent(
    donations: List<Donation>,
    animals: List<Animal>,
    campaigns: List<Campaign>,
    onDonationClick: (Donation) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(donations) { index, donation ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            }
            val animal = donation.animal_id?.let { animalId ->
                animals.find { it.animal_id == animalId }
            }
            val campaign = donation.companha_id?.let { campanhaId ->
                campaigns.find { it.campanha_id == campanhaId }
            }
            DonationListItem(
                donation = donation,
                animalName = animal?.nome,
                campaignName = campaign?.nome,
                backgroundColor = backgroundColor,
                onClick = { onDonationClick(donation) }
            )
        }
    }
}

@Composable
fun DonationListItem(
    donation: Donation,
    animalName: String?,
    campaignName: String?,
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
                text = "Doador: ${donation.doador}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Valor: R$ ${donation.valor}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Data: ${donation.data_doacao}",
                style = MaterialTheme.typography.bodySmall
            )
            if (animalName != null) {
                Text(
                    text = "Animal: $animalName",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (campaignName != null) {
                Text(
                    text = "Campanha: $campaignName",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}