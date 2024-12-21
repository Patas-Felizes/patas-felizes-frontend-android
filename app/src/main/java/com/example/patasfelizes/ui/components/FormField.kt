package com.example.patasfelizes.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FormField(
    label: String? = null,
    placeholder: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = false,
    maxLines: Int = 7,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(bottom = 6.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            singleLine = isSingleLine,
            maxLines = maxLines,
            minLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 60.dp)
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = MaterialTheme.colorScheme.onSecondary
            ),
            keyboardOptions = keyboardOptions,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}


