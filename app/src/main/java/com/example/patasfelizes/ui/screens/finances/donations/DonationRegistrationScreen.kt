package com.example.patasfelizes.ui.screens.finances.donations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.patasfelizes.ui.viewmodels.donation.DonationFormViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationRegistrationScreen(
    navController: NavHostController,
    viewModel: DonationFormViewModel = viewModel()
) {
    DonationFormScreen(
        navController = navController,
        onSave = { donation ->
            viewModel.createDonation(donation) {
                navController.navigateUp()
            }
        },
        isEditMode = false
    )
}