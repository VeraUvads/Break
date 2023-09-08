package com.uva.fastapp.alarm_picker

import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uva.fastapp.App
import com.uva.fastapp.AppContainer
import com.uva.fastapp.ext.viewModelFactory
import java.util.Calendar

@Composable
fun AlarmScreen(
    viewModel: AlarmViewModel = viewModel(
        factory = viewModelFactory {
            AlarmViewModel(
                repository = AppContainer.catRepository,
                context = App.instance.applicationContext
            )
        }
    )
) {
    val mContext = LocalContext.current

    // Declaring and initializing a calendar

    val mTime = remember {
        mutableStateOf(
            String.format(
                "%02d:%02d",
                Calendar.getInstance()[Calendar.HOUR_OF_DAY],
                Calendar.getInstance()[Calendar.MINUTE]
            )
        )
    }
    var time by remember { mutableStateOf(Calendar.getInstance().timeInMillis) }

    var show by remember { mutableStateOf(false) }
    if (show) {
        TimePickerDialog(
            mContext,
            { _, mHour: Int, mMinute: Int ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.HOUR_OF_DAY, mHour)
                calendar.set(Calendar.MINUTE, mMinute)
                calendar.set(Calendar.SECOND, 0)
                mTime.value = String.format("%02d:%02d", mHour, mMinute)
                time = calendar.timeInMillis
                show = false
            },
            Calendar.getInstance()[Calendar.HOUR_OF_DAY],
            Calendar.getInstance()[Calendar.MINUTE],
            true
        ).show()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { show = true }) {
            Text(text = "Select time")
        }

        Spacer(modifier = Modifier.size(100.dp))

        Text(text = "Selected Time: ${mTime.value}", fontSize = 30.sp)

        Button(onClick = { viewModel.setAlarm(time) }) {
            Text(text = "Set alarm with cat")
        }
    }
}
