package com.example.patasfelizes.repository

import android.util.Log
import com.example.patasfelizes.api.RetrofitInitializer
import com.example.patasfelizes.models.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskRepository {
    private val TAG = "TaskRepository"

    fun listTasks(onSuccess: (List<Task>) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para listar tarefas")
        val call = RetrofitInitializer().taskService().listTasks()
        call.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao listar tarefas. Status: ${response.code()}")
                    Log.d(TAG, "Dados recebidos: ${response.body()}")
                    response.body()?.let { tasks ->
                        Log.d(TAG, "Quantidade de tarefas recebidas: ${tasks.size}")
                        onSuccess(tasks)
                    } ?: run {
                        Log.e(TAG, "Resposta vazia da API ao listar tarefas")
                        onError("Resposta vazia da API")
                    }
                } else {
                    Log.e(TAG, "Erro ao listar tarefas. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro na requisição: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao listar tarefas", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun getTask(id: Int, onSuccess: (Task) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para buscar tarefa ID: $id")
        val call = RetrofitInitializer().taskService().getTask(id)
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao buscar tarefa ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Dados da tarefa: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao buscar tarefa ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao obter detalhes: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao buscar tarefa ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun createTask(task: Task, onSuccess: (Task) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para criar tarefa: $task")
        val call = RetrofitInitializer().taskService().createTask(task)
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao criar tarefa. Status: ${response.code()}")
                    Log.d(TAG, "Tarefa criada: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao criar tarefa. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao criar tarefa: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao criar tarefa", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun updateTask(id: Int, task: Task, onSuccess: (Task) -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para atualizar tarefa ID: $id")
        Log.d(TAG, "Dados para atualização: $task")
        val call = RetrofitInitializer().taskService().updateTask(id, task)
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao atualizar tarefa ID: $id. Status: ${response.code()}")
                    Log.d(TAG, "Tarefa atualizada: ${response.body()}")
                    response.body()?.let { onSuccess(it) }
                } else {
                    Log.e(TAG, "Erro ao atualizar tarefa ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao atualizar tarefa: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Task>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao atualizar tarefa ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }

    fun deleteTask(id: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        Log.d(TAG, "Iniciando requisição para deletar tarefa ID: $id")
        val call = RetrofitInitializer().taskService().deleteTask(id)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "Sucesso ao deletar tarefa ID: $id. Status: ${response.code()}")
                    onSuccess()
                } else {
                    Log.e(TAG, "Erro ao deletar tarefa ID: $id. Status: ${response.code()}")
                    Log.e(TAG, "Erro body: ${response.errorBody()?.string()}")
                    onError("Erro ao deletar tarefa: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Falha na conexão ao deletar tarefa ID: $id", t)
                Log.e(TAG, "Stack trace: ${t.stackTraceToString()}")
                onError("Falha na conexão: ${t.message}")
            }
        })
    }
}