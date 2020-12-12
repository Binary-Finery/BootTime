package com.spencer_studios.boottime

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        toolbar.visibility = View.GONE
        handler = Handler()
        textViewDateTime.text = getInfo()
    }

    private val runner = object : Runnable {
        override fun run() {
            handler.postDelayed(this, 1000L)
            textViewUptime.text = formatUptime()
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runner)
    }

    override fun onStart() {
        super.onStart()
        handler.post(runner)
    }
}