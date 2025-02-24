package com.example.patasfelizes.ui.viewmodels.procedure

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.repository.ProcedureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProcedureListViewModel : ViewModel() {
    private val repository = ProcedureRepository()
    private val _procedures = MutableStateFlow<List<Procedure>>(emptyList())
    val procedures: StateFlow<List<Procedure>> = _procedures.asStateFlow()

    init {
        loadProcedures()
    }

    fun reloadProcedures() {
        loadProcedures()
    }

    private fun loadProcedures() {
        repository.listProcedures(
            onSuccess = { procedureList ->
                _procedures.value = procedureList
            },
            onError = { error ->
                Log.e("ProcedureListViewModel", "Error loading procedures: $error")
                _procedures.value = emptyList()
            }
        )
    }
}
