package com.example.patasfelizes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSearchBar(
    searchQuery: TextFieldValue,
    onSearchQueryChanged: (TextFieldValue) -> Unit,
    placeholderText: String = "Pesquisar...",
    onClearSearch: () -> Unit = {},
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    iconSize: Modifier = Modifier.size(28.dp)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            singleLine = true,
            cursorBrush = SolidColor(cursorColor),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp, max = 52.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            textStyle = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 15.sp
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Ícone de Busca",
                        modifier = iconSize.padding(end = 12.dp)
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        if (searchQuery.text.isEmpty()) {
                            Text(
                                text = placeholderText,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
                            )
                        }
                        innerTextField()
                    }
                    if (searchQuery.text.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Limpar Busca",
                            modifier = iconSize
                                .padding(start = 12.dp)
                                .clickable { onClearSearch() }
                        )
                    }
                }
            }
        )
    }
}
