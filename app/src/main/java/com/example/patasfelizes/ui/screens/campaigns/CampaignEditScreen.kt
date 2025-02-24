package com.example.patasfelizes.ui.screens.campaigns

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignFormState
import com.example.patasfelizes.ui.viewmodels.campaigns.CampaignFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignEditScreen(
    navController: NavHostController,
    campaign: Campaign,
    viewModel: CampaignFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is CampaignFormState.Error -> {
                val errorMessage = (state as CampaignFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    CampaignFormScreen(
        navController = navController,
        initialCampaign = campaign,
        onSave = { updatedCampaign ->
            viewModel.updateCampaign(updatedCampaign) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}