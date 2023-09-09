package com.uva.abreak.timer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uva.abreak.R
import com.uva.abreak.codegen_test.Foo
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TimerActivity : AppCompatActivity() {
    lateinit var timerJob: Job
    val viewModel: TimerViewModel by viewModels()
    val hjkh = Foo()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val buttonNext = findViewById<Button>(R.id.next)
        buttonNext.setOnClickListener {
            val intent = Intent(baseContext, EmptyActivity::class.java)
            baseContext.startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        timerJob.cancel()
    }

    override fun onResume() {
        super.onResume()
        val textView = findViewById<TextView>(R.id.timer)
        Log.i("TAG", "onResume  startfrom ${viewModel.startFrom}")
        timerJob = lifecycleScope.launch {
            viewModel.startFrom

            viewModel.state.collect {
                Log.i("TAG", "onResume: $it + startfrom ${viewModel.startFrom}")
                textView.text = it.toString()
            }
        }

    }
}
