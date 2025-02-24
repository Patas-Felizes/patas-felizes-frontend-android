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
import com.example.patasfelizes.models.Extense

@Composable
fun ExtensesContent(
    expenses: List<Extense>,
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
            ExtenseListItem(
                extense = expense,
                backgroundColor = backgroundColor,
                onClick = { onExtenseClick(expense) }
            )
        }
    }
}

@Composable
fun ExtenseListItem(
    extense: Extense,
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
            if (extense.animal_id != null) {
                Text(
                    text = "ID do Animal: ${extense.animal_id}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (extense.procedimento_id != null) {
                Text(
                    text = "ID do Procedimento: ${extense.procedimento_id}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}