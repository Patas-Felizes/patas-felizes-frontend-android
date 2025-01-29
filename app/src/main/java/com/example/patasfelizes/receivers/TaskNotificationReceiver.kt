package com.example.patasfelizes.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.patasfelizes.PatasFelizesApp
import com.example.patasfelizes.R

class TaskNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("NotificationTest", "Receiver acionado")

        val taskId = intent.getIntExtra("taskId", -1)
        val taskType = intent.getStringExtra("taskType") ?: "Tarefa"
        val taskDescription = intent.getStringExtra("taskDescription") ?: "Descrição não disponível"

        Log.d("NotificationTest", "TaskId: $taskId, Type: $taskType")

        val notification = NotificationCompat.Builder(context, PatasFelizesApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.defaul_patas)
            .setContentTitle("Lembrete de Tarefa: $taskType")
            .setContentText(taskDescription)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .build()

        try {
            NotificationManagerCompat.from(context).notify(taskId, notification)
            Log.d("NotificationTest", "Notificação mostrada com sucesso")
        } catch (e: SecurityException) {
            Log.e("NotificationTest", "Erro ao mostrar notificação: ${e.message}")
        }
    }
}