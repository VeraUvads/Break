package com.uva.fastapp.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.uva.fastapp.AppContainer
import com.uva.fastapp.alarm_picker.AlarmItem
import com.uva.fastapp.alarm_picker.AndroidAlarmScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val ALARM_TIME_KEY = "ALARM_TIME_KEY"

class PreloadPhotoWorker(
    private val appContext: Context,
    private val workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {
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
}
