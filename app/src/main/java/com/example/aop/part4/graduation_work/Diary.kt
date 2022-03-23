package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.aop.part4.graduation_work.databinding.DiaryBinding
import com.example.aop.part4.graduation_work.databinding.NewAccountBinding

class Diary: AppCompatActivity() {

    private lateinit var binding: DiaryBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
        finish()
    }
}