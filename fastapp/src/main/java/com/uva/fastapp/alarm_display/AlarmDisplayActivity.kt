package com.uva.fastapp.alarm_display

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.uva.fastapp.main.PhotoItem
import com.uva.fastapp.ui.theme.BreakTheme


class AlarmDisplayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide the status bar and navigation bar to create a full-screen experience.

        // Hide the status bar and navigation bar to create a full-screen experience.
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE
        val message = intent?.getStringExtra("message")!!
        val image = intent?.getStringExtra("image")!!
        val mediaPlayer: MediaPlayer =
            MediaPlayer.create(
                this,
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ) // Use the correct sound resource

        mediaPlayer.isLooping = true // Set looping to false (play sound only once)
        mediaPlayer.setOnCompletionListener { mp ->
            mp.release() // Release the MediaPlayer resources
        }

        // Start playing the sound

        // Start playing the sound
        mediaPlayer.start()
        setContent {
            BreakTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = message, color = Color.White)
                        PhotoItem(photo = image)
                        Button(onClick = {
                            mediaPlayer.release()
                            finish()
                        }) {
                            Text(text = "Stop")
                        }
                    }
                }
            }
        }
    }

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BreakTheme {
        Greeting("Android")
    }
}
