package com.uva.fastapp.alarm_picker

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.BackoffPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.uva.fastapp.domain.CatRepository
import com.uva.fastapp.worker.ALARM_TIME_KEY
import com.uva.fastapp.worker.PreloadPhotoWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class AlarmViewModel(private val repository: CatRepository, private val appContext: Context) :
    ViewModel() {

    fun setAlarm(time: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<PreloadPhotoWorker>()
                    .setInputData(
                        workDataOf(ALARM_TIME_KEY to time),
                    )
                    .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                    .setBackoffCriteria(
                        backoffPolicy = BackoffPolicy.LINEAR,
                        backoffDelay = 100L,
                        timeUnit = TimeUnit.MILLISECONDS,
                    )
                    .build()

                WorkManager
                    .getInstance(appContext)
                    .enqueue(uploadWorkRequest)
            }
        }
    }
}
