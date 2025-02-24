package com.example.patasfelizes.ui.viewmodels.campaigns

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Campaign
import com.example.patasfelizes.repository.CampaignsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class CampaignDetailsState {
    object Loading : CampaignDetailsState()
    data class Success(val campaign: Campaign, val isDeleting: Boolean = false) : CampaignDetailsState()
    data class Error(val message: String) : CampaignDetailsState()
}

class CampaignDetailsViewModel : ViewModel() {
    private val repository = CampaignsRepository()
    private val _uiState = MutableStateFlow<CampaignDetailsState>(CampaignDetailsState.Loading)
    val uiState: StateFlow<CampaignDetailsState> = _uiState.asStateFlow()

    fun loadCampaign(id: Int) {
        _uiState.value = CampaignDetailsState.Loading
        repository.getCampaign(
            id = id,
            onSuccess = { campaign ->
                _uiState.value = CampaignDetailsState.Success(campaign)
            },
            onError = { error ->
                _uiState.value = CampaignDetailsState.Error(error)
            }
        )
    }

    fun deleteCampaign(id: Int, onComplete: () -> Unit) {
        val currentState = _uiState.value
        if (currentState is CampaignDetailsState.Success) {
            _uiState.value = currentState.copy(isDeleting = true)

            repository.deleteCampaign(
                id = id,
                onSuccess = {
                    onComplete()
                },
                onError = { error ->
                    _uiState.value = CampaignDetailsState.Error(error)
                }
            )
        }
    }
}