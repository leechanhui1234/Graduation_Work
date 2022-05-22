package com.example.aop.part4.graduation_work

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class IntroActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro)
        val imageView = findViewById<ImageView>(R.id.logoimage)
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.white))
        val handler = Handler()
        handler.postDelayed(Runnable {
             run {
                 val intent = Intent(this, LoginActivity::class.java)
                 startActivity(intent)
                 finish()
             }
        }, 1000)
    }
}