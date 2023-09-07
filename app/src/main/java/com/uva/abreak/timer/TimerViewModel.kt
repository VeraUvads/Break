package com.uva.abreak.timer

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class TimerViewModel : ViewModel() {

    var startFrom = 0
    val state = (0..Int.MAX_VALUE)
        .asFlow()
        .onEach {
            delay(1000)
            startFrom = it
        }
        .cancellable()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            0
        )

    override fun onCleared() {
        super.onCleared()
        Log.i("TAG", "TimerViewModel onCleared")
    }


//    fun pauseTimer() {
//    }
//
//    fun resumeTimer() {
//        if (!isTimerRunning) {
//            startTimer(1000L) // Replace with your desired delay
//        }
//    }
}
