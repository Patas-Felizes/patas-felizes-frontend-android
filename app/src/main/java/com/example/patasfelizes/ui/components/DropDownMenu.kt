package com.example.patasfelizes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.patasfelizes.R
import kotlinx.coroutines.flow.collect


// posteriormente implementar a função de digitar neste campo
@Composable
fun CustomDropdown(
    modifier: Modifier = Modifier,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        if (!label.isNullOrEmpty()) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            placeholder = if (placeholder != null) {
                {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 15.6.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else null,
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(16.dp),
            trailingIcon = {
                Image(
                    painter = painterResource(
                        id = if (expanded) R.drawable.drop_up else R.drawable.drop_down
                    ),
                    contentDescription = if (expanded) "Recolher" else "Expandir",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            expanded = !expanded
                        }
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.onSecondary
            ),
            textStyle = MaterialTheme.typography.bodyMedium
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .background(MaterialTheme.colorScheme.secondary)
                .clip(RoundedCornerShape(16.dp))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
