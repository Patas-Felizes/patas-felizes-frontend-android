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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


data class FilterOption(
    val name: String,
    val isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterComponent(
    filterOptions: List<FilterOption>,
    onFilterChanged: (List<FilterOption>) -> Unit,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    screenContent: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                FilterDrawerContent(
                    filterOptions = filterOptions,
                    onFilterChanged = onFilterChanged,
                    drawerState = drawerState
                )
            }
        },
        content = screenContent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDrawerContent(
    filterOptions: List<FilterOption>,
    onFilterChanged: (List<FilterOption>) -> Unit,
    drawerState: DrawerState
) {
    val mutableFilterOptions = remember {
        mutableStateOf(filterOptions.toMutableList())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Filtros",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(mutableFilterOptions.value) { filter ->
                FilterOptionItem(
                    filter = filter,
                    onToggle = { updatedFilter ->
                        val updatedOptions = mutableFilterOptions.value.map {
                            if (it.name == updatedFilter.name) updatedFilter else it
                        }
                        mutableFilterOptions.value = updatedOptions.toMutableList()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    mutableFilterOptions.value = filterOptions.toMutableList()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Limpar")
            }

            Button(
                onClick = {
                    onFilterChanged(mutableFilterOptions.value)
                    // Close drawer
                }
            ) {
                Text("Aplicar")
            }
        }
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
fun FilterHeader(
    appliedFilters: List<FilterOption>,
    onFilterIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Filtros aplicados exibidos
        LazyRow(
            modifier = Modifier.fillMaxWidth(0.90f),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(appliedFilters.filter { it.isSelected }) { filter ->
                FilterChip(
                    text = filter.name,
                    onClick = {
                        // Implementação de remover filtro
                    }
                )
            }
        }

        // Ícone de filtro
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
fun FilterChip(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onTertiary
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove Filter",
            modifier = Modifier
                .size(16.dp)
                .clickable { onClick() },
            tint = Color.Gray
        )
    }
}