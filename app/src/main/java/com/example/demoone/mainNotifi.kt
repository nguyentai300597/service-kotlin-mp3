package com.example.demoone

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class mainNotifi: AppCompatActivity() {
    lateinit var viewimg: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_notification)
        viewimg= findViewById<View>(R.id.image) as ImageView
        viewimg.setOnClickListener(){
            print("12312312321321")
        }
    }

    fun myFancyMethod(v: View?) {
        print("12312312321321")
    }


}