package com.example.patasfelizes.api.services

import com.example.patasfelizes.models.Task
import retrofit2.Call
import retrofit2.http.*

interface TaskService {
    @GET("tarefas")
    fun listTasks(): Call<List<Task>>

    @GET("tarefas/{tarefa_id}")
    fun getTask(@Path("tarefa_id") id: Int): Call<Task>

    @POST("tarefas")
    fun createTask(@Body task: Task): Call<Task>

    @PUT("tarefas/{tarefa_id}")
    fun updateTask(@Path("tarefa_id") id: Int, @Body task: Task): Call<Task>

    @DELETE("tarefas/{tarefa_id}")
    fun deleteTask(@Path("tarefa_id") id: Int): Call<Void>
}