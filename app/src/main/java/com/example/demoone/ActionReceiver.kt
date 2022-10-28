package com.example.demoone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class ActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {


        val acticon=intent.getStringExtra("inputExtra")

        if(acticon.equals("button2")){
            Toast.makeText(context,"recieved",Toast.LENGTH_SHORT).show();
            val playMusic = Intent(context, MusicService::class.java)
            context.stopService(playMusic)
        }
        //This is used to close the notification tray
    }

    fun performAction1() {}
    fun performAction2() {}
}