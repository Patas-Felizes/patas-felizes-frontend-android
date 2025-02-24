package com.example.patasfelizes.ui.viewmodels.campaigns

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.repository.CampaignsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class CampaignFormState {
    object Idle : CampaignFormState()
    object Loading : CampaignFormState()
    data class Success(val campaign: Campaign) : CampaignFormState()
    data class Error(val message: String) : CampaignFormState()
}

class CampaignFormViewModel : ViewModel() {
    private val repository = CampaignsRepository()
    private val _state = MutableStateFlow<CampaignFormState>(CampaignFormState.Idle)
    val state: StateFlow<CampaignFormState> = _state.asStateFlow()

    fun createCampaign(campaign: Campaign, onComplete: () -> Unit) {
        _state.value = CampaignFormState.Loading
        repository.createCampaign(
            campaign = campaign,
            onSuccess = { createdCampaign ->
                _state.value = CampaignFormState.Success(createdCampaign)
                onComplete()
            },
            onError = { error ->
                _state.value = CampaignFormState.Error(error)
            }
        )
    }

    fun updateCampaign(campaign: Campaign, onComplete: () -> Unit) {
        _state.value = CampaignFormState.Loading
        repository.updateCampaign(
            id = campaign.campanha_id,
            campaign = campaign,
            onSuccess = { updatedCampaign ->
                _state.value = CampaignFormState.Success(updatedCampaign)
                onComplete()
            },
            onError = { error ->
                _state.value = CampaignFormState.Error(error)
            }
        )
    }
}