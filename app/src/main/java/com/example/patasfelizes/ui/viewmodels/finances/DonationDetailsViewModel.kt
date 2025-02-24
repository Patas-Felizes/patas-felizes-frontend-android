package com.example.patasfelizes.ui.viewmodels.donation

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.repository.DonationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class DonationDetailsState {
    object Loading : DonationDetailsState()
    data class Success(val donation: Donation, val isDeleting: Boolean = false) : DonationDetailsState()
    data class Error(val message: String) : DonationDetailsState()
}

class DonationDetailsViewModel : ViewModel() {
    private val repository = DonationRepository()
    private val _uiState = MutableStateFlow<DonationDetailsState>(DonationDetailsState.Loading)
    val uiState: StateFlow<DonationDetailsState> = _uiState.asStateFlow()

    fun loadDonation(id: Int) {
        _uiState.value = DonationDetailsState.Loading
        repository.getDonation(
            id = id,
            onSuccess = { donation ->
                _uiState.value = DonationDetailsState.Success(donation)
            },
            onError = { error ->
                _uiState.value = DonationDetailsState.Error(error)
            }
        )
    }

    fun deleteDonation(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is DonationDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)

            repository.deleteDonation(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = DonationDetailsState.Error(error)
                }
            )
        }
    }
}