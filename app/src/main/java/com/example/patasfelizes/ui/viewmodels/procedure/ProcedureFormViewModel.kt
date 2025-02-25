package com.example.patasfelizes.ui.viewmodels.procedure

import androidx.lifecycle.ViewModel
import com.example.patasfelizes.models.Procedure
import com.example.patasfelizes.repository.ProcedureRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class ProcedureFormState {
    object Idle : ProcedureFormState()
    object Loading : ProcedureFormState()
    data class Success(val procedure: Procedure) : ProcedureFormState()
    data class Error(val message: String) : ProcedureFormState()
}

class ProcedureFormViewModel : ViewModel() {
    private val repository = ProcedureRepository()
    private val _state = MutableStateFlow<ProcedureFormState>(ProcedureFormState.Idle)
    val state: StateFlow<ProcedureFormState> = _state.asStateFlow()

    fun loadProcedure(id: Int, onSuccess: (Procedure) -> Unit) {
        _state.value = ProcedureFormState.Loading
        repository.getProcedure(
            id = id,
            onSuccess = { procedure ->
                onSuccess(procedure)
                _state.value = ProcedureFormState.Idle
            },
            onError = { error ->
                _state.value = ProcedureFormState.Error(error)
            }
        )
    }

    fun createProcedure(procedure: Procedure, onComplete: () -> Unit) {
        _state.value = ProcedureFormState.Loading
        val procedureForCreation = Procedure(
            procedimento_id = 0, // Este valor será ignorado pelo backend
            tipo = procedure.tipo,
            descricao = procedure.descricao,
            valor = procedure.valor,
            data_procedimento = procedure.data_procedimento,
            animal_id = procedure.animal_id,
            voluntario_id = procedure.voluntario_id
        )

        repository.createProcedure(
            procedure = procedureForCreation,
            onSuccess = { createdProcedure ->
                _state.value = ProcedureFormState.Success(createdProcedure)
                onComplete()
            },
            onError = { error ->
                _state.value = ProcedureFormState.Error(error)
            }
        )
    }

    fun updateProcedure(procedure: Procedure, onComplete: () -> Unit) {
        _state.value = ProcedureFormState.Loading
        repository.updateProcedure(
            id = procedure.procedimento_id,
            procedure = procedure,
            onSuccess = { updatedProcedure ->
                _state.value = ProcedureFormState.Success(updatedProcedure)
                onComplete()
            },
            onError = { error ->
                _state.value = ProcedureFormState.Error(error)
            }
        )
    }
}