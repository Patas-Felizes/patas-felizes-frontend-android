package com.example.patasfelizes.ui.viewmodels.donation

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.repository.DonationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class DonationFormState {
    object Idle : DonationFormState()
    object Loading : DonationFormState()
    data class Success(val donation: Donation) : DonationFormState()
    data class Error(val message: String) : DonationFormState()
}

class DonationFormViewModel : ViewModel() {
    private val repository = DonationRepository()
    private val _state = MutableStateFlow<DonationFormState>(DonationFormState.Idle)
    val state: StateFlow<DonationFormState> = _state.asStateFlow()

    fun loadDonation(id: Int, onSuccess: (Donation) -> Unit) {
        _state.value = DonationFormState.Loading
        repository.getDonation(
            id = id,
            onSuccess = { donation ->
                onSuccess(donation)
                _state.value = DonationFormState.Idle
            },
            onError = { error ->
                _state.value = DonationFormState.Error(error)
            }
        )
    }

    fun createDonation(donation: Donation, onComplete: () -> Unit) {
        _state.value = DonationFormState.Loading
        repository.createDonation(
            donation = donation,
            onSuccess = { createdDonation ->
                _state.value = DonationFormState.Success(createdDonation)
                onComplete()
            },
            onError = { error ->
                _state.value = DonationFormState.Error(error)
            }
        )
    }

    fun updateDonation(donation: Donation, onComplete: () -> Unit) {
        _state.value = DonationFormState.Loading
        repository.updateDonation(
            id = donation.doacao_id,
            donation = donation,
            onSuccess = { updatedDonation ->
                _state.value = DonationFormState.Success(updatedDonation)
                onComplete()
            },
            onError = { error ->
                _state.value = DonationFormState.Error(error)
            }
        )
    }
}