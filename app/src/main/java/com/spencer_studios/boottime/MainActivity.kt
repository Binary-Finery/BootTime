package com.spencer_studios.boottime

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var droids: Array<ImageView>
    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        toolbar.visibility = View.GONE
        handler = Handler()
        initDateTimeValues()

        droids = arrayOf(droid1, droid2, droid3)

        droids.forEach {
            it.setOnClickListener { bot ->
                droidClick(bot as ImageView)
            }
        }
    }

    private fun initDateTimeValues(){
        val values = getInfo()

        textViewDevice.text = values[0]
        textViewBootedOn.text = values[1]
        textViewBootDate.text = values[2]
        textViewBootTime.text = values[3]
    }

    private fun droidClick(imageView: ImageView) {
        imageView.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.droid_anim
            )
        )
        stopPlaying()
        startPlaying()
    }

    private val runner = object : Runnable {
        override fun run() {
            handler.postDelayed(this, 1000L)
            updateUI()
        }
    }

    private fun updateUI(){
        textViewUptime.text = formatUptime()
        val dateMeta = formatDate(System.currentTimeMillis() - SystemClock.elapsedRealtime())
        textViewBootedOn.text = dateMeta[0]
        textViewBootDate.text = dateMeta[1]
    }

    override fun onStart() {
        super.onStart()
        dateTimeParent.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide))
        uptimeParent.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
        startPlaying()
        handler.post(runner)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runner)
        stopPlaying()
    }

    private fun stopPlaying() {
        mp.stop()
        mp.release()
    }

    private fun startPlaying() {
        mp = MediaPlayer.create(this, R.raw.start)
        mp.start()
    }
}