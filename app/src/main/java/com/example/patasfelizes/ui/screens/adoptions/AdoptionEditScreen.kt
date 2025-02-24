// AdoptionEditScreen.kt
package com.example.patasfelizes.ui.screens.adoptions

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.Adoption
import com.example.patasfelizes.ui.viewmodels.adoptions.AdoptionFormViewModel

@Composable
fun AdoptionEditScreen(
    navController: NavHostController,
    adoptionId: Int,
    viewModel: AdoptionFormViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    AdoptionFormScreen(
        navController = navController,
        onSave = { updatedAdoption ->
            viewModel.updateAdoption(adoptionId, updatedAdoption) {
                navController.navigateUp()
            }
        },
        isEditMode = true
    )
}