package com.example.patasfelizes.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.patasfelizes.PatasFelizesApp
import com.example.patasfelizes.R
import com.example.patasfelizes.receivers.TaskNotificationReceiver
import java.util.Calendar

const val REQUEST_NOTIFICATION_PERMISSION = 123

fun checkNotificationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        NotificationManagerCompat.from(context).areNotificationsEnabled()
    }
}

fun requestNotificationPermission(activity: ComponentActivity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_NOTIFICATION_PERMISSION
        )
    } else {
        val intent = Intent().apply {
            action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            putExtra(Settings.EXTRA_APP_PACKAGE, activity.packageName)
        }
        activity.startActivity(intent)
    }
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleTaskNotification(context: Context, taskId: Int, taskType: String, taskDescription: String, taskDate: String) {
    Log.d("NotificationTest", "Iniciando agendamento de notificação")

    if (!checkNotificationPermission(context)) {
        Log.d("NotificationTest", "Notificações não permitidas")
        return
    }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // Verificar permissão para alarmes exatos no Android 12+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
            Log.d("NotificationTest", "Permissão de alarme exato necessária")
            return
        }
    }

    val intent = Intent(context, TaskNotificationReceiver::class.java).apply {
        putExtra("taskId", taskId)
        putExtra("taskType", taskType)
        putExtra("taskDescription", taskDescription)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        taskId,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        val parts = taskDate.split("-")
        set(Calendar.DAY_OF_MONTH, parts[0].toInt())
        set(Calendar.MONTH, parts[1].toInt() - 1)
        set(Calendar.YEAR, parts[2].toInt())
        set(Calendar.HOUR_OF_DAY, 9)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )

    Log.d("NotificationTest", "Notificação agendada para: ${calendar.time}")
}

fun cancelTaskNotification(context: Context, taskId: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, TaskNotificationReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        taskId,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
}

fun showInstantNotification(context: Context, taskId: Int, taskType: String, taskDescription: String) {
    if (!checkNotificationPermission(context)) {
        Log.d("NotificationTest", "Notificações não permitidas")
        return
    }

    val notification = NotificationCompat.Builder(context, PatasFelizesApp.CHANNEL_ID)
        .setSmallIcon(R.drawable.defaul_patas)
        .setContentTitle("Tarefa Adicionada: $taskType")
        .setContentText(taskDescription)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setVibrate(longArrayOf(1000, 1000, 1000))
        .build()

    try {
        NotificationManagerCompat.from(context).notify(taskId, notification)
        Log.d("NotificationTest", "Notificação instantânea mostrada com sucesso")
    } catch (e: SecurityException) {
        Log.e("NotificationTest", "Erro ao mostrar notificação: ${e.message}")
    }
}