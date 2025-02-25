package com.example.patasfelizes.ui.screens.finances.extenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.models.Procedure

@Composable
fun ExtensesContent(
    expenses: List<Extense>,
    animals: List<Animal>,
    onExtenseClick: (Extense) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        itemsIndexed(expenses) { index, expense ->
            val backgroundColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            }
            val animal = expense.animal_id?.let { animalId ->
                animals.find { it.animal_id == animalId }
            }

            ExtenseListItem(
                extense = expense,
                animalName = animal?.nome,
                backgroundColor = backgroundColor,
                onClick = { onExtenseClick(expense) }
            )
        }
    }
}

@Composable
fun ExtenseListItem(
    extense: Extense,
    animalName: String?,
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
                text = "Tipo: ${extense.tipo}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Valor: R$ ${extense.valor}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Data: ${extense.data_despesa}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (animalName != null) {
                Text(
                    text = "Animal: $animalName",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}