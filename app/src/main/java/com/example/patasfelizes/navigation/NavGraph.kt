package com.example.patasfelizes.navigation

import java.time.LocalDate
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patasfelizes.models.Adopter
import com.example.patasfelizes.models.AdopterList
import com.example.patasfelizes.models.Animal
import com.example.patasfelizes.models.AnimalList
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.models.DonationList
import com.example.patasfelizes.models.Extense
import com.example.patasfelizes.models.ExtenseList
import com.example.patasfelizes.models.Voluntary
import com.example.patasfelizes.models.VoluntaryList
import com.example.patasfelizes.ui.screens.adoptions.AdoptionsScreen
import com.example.patasfelizes.ui.screens.animals.AnimalEditScreen
import com.example.patasfelizes.ui.screens.animals.AnimalFormScreen
import com.example.patasfelizes.ui.screens.adoptions.DetailsAdoptionsScreen
import com.example.patasfelizes.ui.screens.adoptions.AdoptionRegistrationScreen
import com.example.patasfelizes.ui.screens.adoptions.AdoptionFormScreen
import com.example.patasfelizes.ui.screens.adoptions.AdoptionEditScreen
import com.example.patasfelizes.ui.screens.animals.AnimalScreen
import com.example.patasfelizes.ui.screens.animals.DetailsAnimalScreen
import com.example.patasfelizes.ui.screens.animals.AnimalRegistrationScreen
import com.example.patasfelizes.ui.screens.campaigns.CampaignsScreen
import com.example.patasfelizes.ui.screens.finances.DetailsDonationScreen
import com.example.patasfelizes.ui.screens.finances.DetailsExtenseScreen
import com.example.patasfelizes.ui.screens.finances.DonationFormScreen
import com.example.patasfelizes.ui.screens.finances.ExtenseFormScreen
import com.example.patasfelizes.ui.screens.finances.FinancesScreen
import com.example.patasfelizes.ui.screens.procedures.ProceduresScreen
import com.example.patasfelizes.ui.screens.stock.StockScreen
import com.example.patasfelizes.ui.screens.tasks.TasksScreen
import com.example.patasfelizes.ui.screens.team.TeamScreen
import com.example.patasfelizes.ui.screens.reports.ReportsScreen
import com.example.patasfelizes.ui.screens.support.SupportScreen
import com.example.patasfelizes.ui.screens.team.DetailsTeamScreen
import com.example.patasfelizes.ui.screens.team.TeamFormScreen
import com.example.patasfelizes.ui.screens.temporaryhomes.TemporaryHomesScreen

fun NavGraphBuilder.setupNavHost(
    navController: NavHostController,
    onSaveAnimal: (Animal) -> Unit,
    onSaveVoluntary: (Voluntary) -> Unit,
    onSaveExtense: (Extense) -> Unit,
    onSaveDonation: (Donation) -> Unit

) {

    //Navegação da Tela Pets
    composable("pets") {
        AnimalScreen(navController = navController)
    }
    composable(
        route = "animalDetails/{animalId}",
        arguments = listOf(navArgument("animalId") { type = NavType.IntType })
    ) { backStackEntry ->
        val animalId = backStackEntry.arguments?.getInt("animalId")
        animalId?.let {
            DetailsAnimalScreen(navController, animalId = it)
        }
    }

    composable("addAnimal") {
        AnimalFormScreen(
            navController = navController,
            onSave = onSaveAnimal,
            isEditMode = false
        )
    }

    composable(
        route = "editAnimal/{animalId}",
        arguments = listOf(navArgument("animalId") { type = NavType.IntType })
    ) { backStackEntry ->
        val animalId = backStackEntry.arguments?.getInt("animalId") ?: return@composable
        val animal = AnimalList.find { it.id == animalId } ?: return@composable

        AnimalFormScreen(
            navController = navController,
            initialAnimal = animal,
            onSave = { updatedAnimal ->
                // Atualizar o animal na lista
                val index = AnimalList.indexOfFirst { it.id == animalId }
                if (index != -1) {
                    AnimalList[index] = updatedAnimal
                }
            },
            isEditMode = true
        )
    }

    composable("adocoes") {
        AdoptionsScreen(navController = navController)
    }
    composable(
        route = "adoptionDetails/{adopterId}",
        arguments = listOf(navArgument("adopterId") { type = NavType.IntType })
    ) { backStackEntry ->
        val adopterId = backStackEntry.arguments?.getInt("adopterId")
        adopterId?.let {
            DetailsAdoptionsScreen(navController = navController, adopterId = it, animalId = it)
        }
    }
    composable("addAdoption") {
        AdoptionRegistrationScreen(
            navController = navController,
            onSave = { pet, name, contact, state, city, address, neighborhood, number, cep ->
                AdopterList.add(
                    Adopter(
                        id = AdopterList.size + 1, nome = name, petNome = pet, telefone = contact,
                        estado = state, cidade = city, endereco = address, bairro = neighborhood,
                        numero = number, cep = cep, dataCadastro = LocalDate.now()
                    )
                )
            }
        )
    }

    composable(
        route = "editAdoption/{adoptionId}",
        arguments = listOf(navArgument("adoptionId") { type = NavType.IntType })
    ) { backStackEntry ->
        val adoptionId = backStackEntry.arguments?.getInt("adoptionId") ?: return@composable
        val adopter = AdopterList.find { it.id == adoptionId } ?: return@composable

        AdoptionEditScreen(
            navController = navController,
            adopter = adopter,
            onSave = { pet, name, contact, state, city, address, neighborhood, number, cep ->
                val index = AdopterList.indexOfFirst { it.id == adoptionId }
                if (index != -1) {
                    AdopterList[index] = adopter.copy(
                        petNome = pet, nome = name, telefone = contact, estado = state,
                        cidade = city, endereco = address, bairro = neighborhood,
                        numero = number, cep = cep
                    )
                }
            }
        )
    }


    composable("lar_temporario") {
        TemporaryHomesScreen(navController = navController)
    }
    composable("apadrinhamento") {
        SupportScreen(navController = navController)
    }
    composable("procedimentos") {
        ProceduresScreen(navController = navController)
    }
    composable("campanhas") {
        CampaignsScreen(navController = navController)
    }
    composable("financas") {
        FinancesScreen(navController = navController)
    }

    composable(
        route = "extenseDetails/{extenseId}",
        arguments = listOf(navArgument("extenseId") { type = NavType.IntType })
    ) { backStackEntry ->
        val extenseId = backStackEntry.arguments?.getInt("extenseId")
        extenseId?.let {
            DetailsExtenseScreen(navController, extenseId = it)
        }
    }

    composable(
        route = "donationDetails/{donationId}",
        arguments = listOf(navArgument("donationId") { type = NavType.IntType })
    ) { backStackEntry ->
        val donationId = backStackEntry.arguments?.getInt("donationId")
        donationId?.let {
            DetailsDonationScreen(navController, donationId = it)
        }
    }

    composable("addExtense") {
        ExtenseFormScreen(
            navController = navController,
            onSave = onSaveExtense,
            isEditMode = false
        )
    }

    composable("addDonation") {
        DonationFormScreen(
            navController = navController,
            onSave = onSaveDonation,
            isEditMode = false
        )
    }

    composable(
        route = "editExtense/{extenseId}",
        arguments = listOf(navArgument("extenseId") { type = NavType.IntType })
    ) { backStackEntry ->
        val extenseId = backStackEntry.arguments?.getInt("extenseId") ?: return@composable
        val extense = ExtenseList.find { it.id == extenseId } ?: return@composable

        ExtenseFormScreen(
            navController = navController,
            initialExtense = extense,
            onSave = { updatedExtense ->
                // Atualizar o animal na lista
                val index = ExtenseList.indexOfFirst { it.id == extenseId }
                if (index != -1) {
                    ExtenseList[index] = updatedExtense
                }
            },
            isEditMode = true
        )
    }

    composable(
        route = "editDonation/{donationId}",
        arguments = listOf(navArgument("donationId") { type = NavType.IntType })
    ) { backStackEntry ->
        val donationId = backStackEntry.arguments?.getInt("donationId") ?: return@composable
        val donation = DonationList.find { it.id == donationId } ?: return@composable

        DonationFormScreen(
            navController = navController,
            initialDonation = donation,
            onSave = { updatedDonation ->
                // Atualizar o animal na lista
                val index = DonationList.indexOfFirst { it.id == donationId }
                if (index != -1) {
                    DonationList[index] = updatedDonation
                }
            },
            isEditMode = true
        )
    }

    composable("estoque") {
        StockScreen(navController = navController)
    }
    composable("tarefas") {
        TasksScreen(navController = navController)
    }
    composable("equipe") {
        TeamScreen(navController = navController)
    }
    composable(
        route = "voluntaryDetails/{voluntaryId}",
        arguments = listOf(navArgument("voluntaryId") { type = NavType.IntType })
    ) { backStackEntry ->
        val voluntaryId = backStackEntry.arguments?.getInt("voluntaryId")
        voluntaryId?.let {
            DetailsTeamScreen(navController, voluntaryId = it)
        }
    }

    composable("addVoluntary") {
        TeamFormScreen(
            navController = navController,
            onSave = onSaveVoluntary,
            isEditMode = false
        )
    }

    composable(
        route = "editVoluntary/{voluntaryId}",
        arguments = listOf(navArgument("voluntaryId") { type = NavType.IntType })
    ) { backStackEntry ->
        val voluntaryId = backStackEntry.arguments?.getInt("voluntaryId") ?: return@composable
        val voluntary = VoluntaryList.find { it.id == voluntaryId } ?: return@composable

        TeamFormScreen(
            navController = navController,
            initialVoluntary = voluntary,
            onSave = { updatedVoluntary ->
                // Atualizar o animal na lista
                val index = VoluntaryList.indexOfFirst { it.id == voluntaryId }
                if (index != -1) {
                    VoluntaryList[index] = updatedVoluntary
                }
            },
            isEditMode = true
        )
    }
    composable("relatorios") {
        ReportsScreen(navController = navController)
    }
}