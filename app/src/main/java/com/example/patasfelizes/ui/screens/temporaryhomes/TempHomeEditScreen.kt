package com.example.patasfelizes.ui.screens.temporaryhomes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.patasfelizes.models.GuardianTemp

@Composable
fun TempHomeEditScreen(
    navController: NavHostController,
    guardian: GuardianTemp,
    onSave: (String, String, String, String, String, String, String, String, String, String) -> Unit
) {
    TempHomeFormScreen(
        initialPetName = guardian.petNome,
        initialGuardianName = guardian.nome,
        initialContactInfo = guardian.telefone,
        initialPeriod = guardian.periodo,
        initialState = guardian.estado,
        initialCity = guardian.cidade,
        initialAddress = guardian.endereco,
        initialNeighborhood = guardian.bairro,
        initialNumber = guardian.numero,
        initialCep = guardian.cep,
        onSave = { petName, guardianName, contactInfo, period, state, city, address, neighborhood, number, cep ->
            onSave(petName, guardianName, contactInfo, period, state, city, address, neighborhood, number, cep)
            navController.navigateUp()
        },
        onCancel = { navController.navigateUp() }
    )
}
