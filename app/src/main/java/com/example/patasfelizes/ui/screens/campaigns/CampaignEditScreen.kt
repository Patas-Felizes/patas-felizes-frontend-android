package com.example.patasfelizes.ui.screens.campaigns

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Campaign

@Composable
fun CampaignEditScreen(
    navController: NavHostController,
    campaign: Campaign,
    onSave: (Campaign) -> Unit
) {
    CampaignFormScreen(
        navController = navController,
        initialCampaign = campaign,
        onSave = onSave,
        isEditMode = true
    )
}