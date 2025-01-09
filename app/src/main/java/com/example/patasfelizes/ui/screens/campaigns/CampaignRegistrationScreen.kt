package com.example.patasfelizes.ui.screens.campaigns

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Campaign


@Composable
fun CampaignRegistrationScreen(
    navController: NavHostController,
    onSave: (Campaign) -> Unit
) {
    CampaignFormScreen(
        navController = navController,
        onSave = onSave,
        isEditMode = false
    )
}