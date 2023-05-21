package com.uva.abreak

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val UPDATE_TEXT_CODE = 1
const val DATA_KEY = "2"

class HandlerExampleActivity : AppCompatActivity() {
    private var bgThread: BackgroundThread? = null

    // or run on ui thread
    private val viewHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                UPDATE_TEXT_CODE -> {
                    findViewById<TextView>(R.id.text).text = msg.data.getString(DATA_KEY)
                }
                else -> {}
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bgThread = BackgroundThread()
        bgThread?.start()

        findViewById<Button>(R.id.button).setOnClickListener {
            bgThread?.handler?.sendMessage(Message.obtain())
        }
    }

    override fun onDestroy() {
        bgThread?.quit()
        bgThread = null
        super.onDestroy()
    }

    inner class BackgroundThread : Thread() {
        lateinit var handler: Handler

        override fun run() {
            super.run()
            Looper.prepare()
            handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    for (i in 0..5) {
                        val message = Message.obtain()
                        message.what = UPDATE_TEXT_CODE
                        message.data = Bundle().apply {
                            putString(DATA_KEY, i.toString())
                        }
                        viewHandler.sendMessage(message)
                        sleep(1000L)
                    }
                }
            }
            Looper.loop()
        }

        fun quit() {
            handler.looper.quit()
        }
    }
}
