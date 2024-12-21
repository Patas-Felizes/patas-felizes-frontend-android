package com.example.patasfelizes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign


@ExperimentalMaterial3Api
@Composable
fun TopBar(
    title: String,
    onOpenDrawer: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        navigationIcon = {
            IconButton(onClick = onOpenDrawer) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Open Menu",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Profile",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}