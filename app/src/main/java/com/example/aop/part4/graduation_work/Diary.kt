package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.DiaryBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Diary: AppCompatActivity() {

    private lateinit var binding : DiaryBinding

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val Date = LocalDate.now()
            Day.text = Date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))

            dairyText.text = null

            dairySave.setOnClickListener {
                //저장
            }

        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
        finish()
    }
}