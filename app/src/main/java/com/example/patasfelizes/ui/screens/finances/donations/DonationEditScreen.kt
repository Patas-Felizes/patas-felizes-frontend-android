package com.example.patasfelizes.ui.screens.finances.donations

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    donationId: Int,
    viewModel: DonationFormViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val (donationLoaded, setDonationLoaded) = remember { mutableStateOf<Donation?>(null) }

    // Carregar a doação quando a tela for inicializada
    LaunchedEffect(donationId) {
        viewModel.loadDonation(donationId) { donation ->
            setDonationLoaded(donation)
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is DonationFormState.Error -> {
                val errorMessage = (state as DonationFormState.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    // Aguardar a doação ser carregada antes de mostrar o formulário
    donationLoaded?.let { donation ->
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
}