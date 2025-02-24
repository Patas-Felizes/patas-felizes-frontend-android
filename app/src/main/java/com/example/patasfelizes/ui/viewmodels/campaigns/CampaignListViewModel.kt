package com.example.patasfelizes.ui.viewmodels.campaigns

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.repository.CampaignsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CampaignListViewModel : ViewModel() {
    private val repository = CampaignsRepository()
    private val _campaigns = MutableStateFlow<List<Campaign>>(emptyList())
    val campaigns: StateFlow<List<Campaign>> = _campaigns.asStateFlow()

    init {
        loadCampaigns()
    }

    fun reloadCampaigns() {
        loadCampaigns()
    }

    private fun loadCampaigns() {
        repository.listCampaigns(
            onSuccess = { campaignList ->
                _campaigns.value = campaignList
            },
            onError = { error ->
                Log.e("CampaignListViewModel", "Error loading campaigns: $error")
                _campaigns.value = emptyList()
            }
        )
    }
}
