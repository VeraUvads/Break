package com.uva.abreak

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

const val showData = "1"

class HandlerExampleActivity : AppCompatActivity() {

    // or run on ui thread
    val viewHandler: Handler = object : Handler(android.os.Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                showData.toInt() -> {
                    findViewById<Button>(R.id.button).text = msg.data.getString(showData)
                }
                else -> {}
            }
        }
    }
    val bgThread: BackroundThread = BackroundThread()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bgThread.start()

        findViewById<Button>(R.id.button).setOnClickListener {
            bgThread.handler?.sendMessage(android.os.Message.obtain())
        }
    }

    inner class BackroundThread : Thread() {
        var handler: Handler? = null

        override fun run() {
            super.run()
            Looper.prepare()
            handler = object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    for (i in 0..5) {
                        val message = Message.obtain()
                        message.what = showData.toInt()
                        message.data = Bundle().apply {
                            putString(showData, i.toString())
                        }
                        viewHandler.sendMessage(message)
                        Thread.sleep(1000L)
                    }
                }
            }
            Looper.loop()
        }

        fun quit() {
            handler?.looper?.quit()
        }
    }
}
