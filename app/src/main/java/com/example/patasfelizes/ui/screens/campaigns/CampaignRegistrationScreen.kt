package com.example.patasfelizes.ui.screens.campaigns

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignRegistrationScreen(
    navController: NavHostController,
    viewModel: CampaignFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    CampaignFormScreen(
        navController = navController,
        onSave = { campaign ->
            viewModel.createCampaign(campaign) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}