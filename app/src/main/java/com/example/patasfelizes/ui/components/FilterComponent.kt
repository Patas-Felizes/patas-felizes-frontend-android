package com.example.patasfelizes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FilterOption(
    val name: String,
    val isSelected: Boolean = false
)

@Composable
fun FilterComponent(
    filterOptions: List<FilterOption>,
    onFilterChanged: (List<FilterOption>) -> Unit,
) {
    var showFilterDialog by remember { mutableStateOf(false) }
    var mutableFilterOptions by remember { mutableStateOf(filterOptions) }

    FilterHeader(
        appliedFilters = mutableFilterOptions,
        onFilterIconClick = { showFilterDialog = true },
        onFilterRemoved = { removedFilter ->

            val updatedFilters = mutableFilterOptions.map {
                if (it.name == removedFilter.name) it.copy(isSelected = false) else it
            }
            mutableFilterOptions = updatedFilters
            onFilterChanged(updatedFilters)
        }
    )

    if (showFilterDialog) {
        FilterOptionsDialog(
            filterOptions = mutableFilterOptions,
            onDismiss = { showFilterDialog = false },
            onFilterChanged = { updatedFilters ->
                mutableFilterOptions = updatedFilters
                onFilterChanged(updatedFilters)
                showFilterDialog = false
            }
        )
    }
}

@Composable
fun FilterOptionsDialog(
    filterOptions: List<FilterOption>,
    onDismiss: () -> Unit,
    onFilterChanged: (List<FilterOption>) -> Unit
) {
    var currentFilterOptions by remember { mutableStateOf(filterOptions) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Filtros",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            LazyColumn {
                items(currentFilterOptions) { filter ->
                    FilterOptionItem(
                        filter = filter,
                        onToggle = { updatedFilter ->
                            currentFilterOptions = currentFilterOptions.map {
                                if (it.name == updatedFilter.name) updatedFilter else it
                            }
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onFilterChanged(currentFilterOptions) }) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        containerColor = MaterialTheme.colorScheme.secondary,
        tonalElevation = 6.dp
    )
}

@Composable
fun FilterHeader(
    appliedFilters: List<FilterOption>,
    onFilterIconClick: () -> Unit,
    onFilterRemoved: (FilterOption) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        LazyRow(
            modifier = Modifier.fillMaxWidth(0.90f),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val selectedFilters = appliedFilters.filter { it.isSelected }
            if (selectedFilters.isNotEmpty()) {
                items(selectedFilters) { filter ->
                    FilterChip(
                        text = filter.name,
                        onRemove = { onFilterRemoved(filter) }
                    )
                }
            } else {
                item {
                    Text(
                        text = "Filtros",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Icon(
            imageVector = Icons.Outlined.FilterList,
            contentDescription = "Filtrar",
            modifier = Modifier
                .size(26.dp)
                .clickable { onFilterIconClick() },
            tint = MaterialTheme.colorScheme.primary
        )
    }
}




@Composable
fun FilterOptionItem(
    filter: FilterOption,
    onToggle: (FilterOption) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onToggle(filter.copy(isSelected = !filter.isSelected))
            }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = filter.isSelected,
            onCheckedChange = {
                onToggle(filter.copy(isSelected = it))
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = filter.name,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun FilterChip(
    text: String,
    onRemove: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable { onRemove() }
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove Filter",
            modifier = Modifier.size(12.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}
