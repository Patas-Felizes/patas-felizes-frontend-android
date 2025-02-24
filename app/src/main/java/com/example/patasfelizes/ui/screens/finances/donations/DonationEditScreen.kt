package com.example.patasfelizes.ui.screens.finances.donations

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.ui.viewmodels.donation.DonationFormState
import com.example.patasfelizes.ui.viewmodels.donation.DonationFormViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationEditScreen(
    navController: NavHostController,
    donation: Donation,
    viewModel: DonationFormViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state) {
        when (state) {
            is DonationFormState.Error -> {
                val errorMessage = (state as DonationFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    DonationFormScreen(
        navController = navController,
        initialDonation = donation,
        onSave = { updatedDonation ->
            viewModel.updateDonation(updatedDonation) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}