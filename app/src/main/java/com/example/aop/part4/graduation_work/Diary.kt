package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.data.UserDiary
import com.example.aop.part4.graduation_work.databinding.DiaryBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Diary: AppCompatActivity() {

    private lateinit var binding : DiaryBinding
    var database = Firebase.database.reference.child("diary")

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val Date = LocalDate.now()
            Day.text = Date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))


            diarySave.setOnClickListener {
                database.push().setValue(UserDiary(diaryText.text.toString(), Day.text.toString()))
                Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Dialist::class.java)
        startActivity(intent)
        finish()
    }
}