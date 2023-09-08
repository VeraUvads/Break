package com.uva.fastapp.alarm_picker

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uva.fastapp.domain.CatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlarmViewModel(private val repository: CatRepository, private val context: Context) :
    ViewModel() {

    private val scheduler = AndroidAlarmScheduler(context)

    fun setAlarm(time: Long) { // todo service
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val catImage = repository.uploadCats(1).firstOrNull()
                withContext(Dispatchers.Main) {
                    scheduler.schedule(
                        AlarmItem(
                            time,
                            "Wake up",
                            catImage?.url.orEmpty()
                        )
                    )
                }
            }
        }
    }
}
