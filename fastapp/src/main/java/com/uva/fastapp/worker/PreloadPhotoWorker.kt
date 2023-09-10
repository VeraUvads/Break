package com.uva.fastapp.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.uva.fastapp.AppContainer
import com.uva.fastapp.R
import com.uva.fastapp.alarm_picker.AlarmItem
import com.uva.fastapp.alarm_picker.AndroidAlarmScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

const val ALARM_TIME_KEY = "ALARM_TIME_KEY"

class PreloadPhotoWorker(
    private val appContext: Context,
    private val workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            748394,
            getNotification(),
        )
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val catImage = AppContainer.catRepository.uploadCats(1).firstOrNull()
            val scheduler = AndroidAlarmScheduler(appContext)
            val time = workerParams.inputData.getLong(ALARM_TIME_KEY, 0L)
            scheduler.schedule(
                AlarmItem(
                    time,
                    "Wake up",
                    catImage?.url.orEmpty(),
                ),
            )
        } catch (exception: Exception) {
            if (exception is InterruptedException) throw exception
            return@withContext Result.retry()
        }
        return@withContext Result.success()
    }

    private fun getNotification(): Notification {
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(appContext, appContext.packageName)
            .setContentTitle("Photo preloading")
            .setContentText("In progress")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSmallIcon(R.drawable.ic_launcher_foreground)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager = appContext.getSystemService(
                NotificationManager::class.java,
            )
            val channel = NotificationChannel(
                applicationContext.packageName,
                "sd",
                NotificationManager.IMPORTANCE_LOW,
            )
            notificationManager.createNotificationChannel(channel)
        }

        return notificationBuilder.build()
    }
}
