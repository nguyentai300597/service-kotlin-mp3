package com.example.demoone

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var play: Button? = null
    private  var stop:Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initControl();

        initEvent();
    }
    private fun initEvent() {
     play!!.setOnClickListener {
            val playMusic = Intent(this@MainActivity, MusicService::class.java)

            startService(playMusic) // Gọi hàm bắt đầu service
            //startForegroundService(playMusic)
          //  ForegroundService.startService(this, "Foreground Service is running...")
        }
        stop!!.setOnClickListener {
            val playMusic = Intent(this@MainActivity, MusicService::class.java)
            stopService(playMusic)
          //  ForegroundService.stopService(this)
        }
    }

    private fun initControl() {
        //Cái này quá quen thuộc rồi khỏi nói ha
        play = findViewById<View>(R.id.play) as Button
        stop = findViewById<View>(R.id.stop) as Button
    }
}