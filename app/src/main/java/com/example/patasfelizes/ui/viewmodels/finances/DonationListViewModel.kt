package com.example.patasfelizes.ui.viewmodels.donation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Donation
import com.example.patasfelizes.repository.DonationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DonationListViewModel : ViewModel() {
    private val repository = DonationRepository()
    private val _donations = MutableStateFlow<List<Donation>>(emptyList())
    val donations: StateFlow<List<Donation>> = _donations.asStateFlow()

    init {
        loadDonations()
    }

    fun reloadDonations() {
        loadDonations()
    }

    private fun loadDonations() {
        repository.listDonations(
            onSuccess = { donationList ->
                _donations.value = donationList
            },
            onError = { error ->
                Log.e("DonationListViewModel", "Error loading donations: $error")
                _donations.value = emptyList()
            }
        )
    }
}