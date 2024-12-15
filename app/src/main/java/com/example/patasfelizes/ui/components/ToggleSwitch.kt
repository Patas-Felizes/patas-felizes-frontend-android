package com.example.patasfelizes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToggleSwitch(
    options: List<String>,
    isSelected: Boolean,
    onOptionSelected: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEachIndexed { index, option ->
            val isOptionSelected = if (index == 0) isSelected else !isSelected

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .background(
                        color = if (isOptionSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable { onOptionSelected(index == 0) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (isOptionSelected) MaterialTheme.colorScheme.onPrimary else Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (index < options.size - 1) Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
