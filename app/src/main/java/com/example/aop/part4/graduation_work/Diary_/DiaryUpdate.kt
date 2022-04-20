package com.example.aop.part4.graduation_work.Diary_

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.aop.part4.graduation_work.data.UserDialist
import com.example.aop.part4.graduation_work.databinding.DiaryUpdateBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DiaryUpdate : AppCompatActivity() {

    private lateinit var binding : DiaryUpdateBinding
    private val diarydatabase = Firebase.database.reference.child("diary")

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DiaryUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = intent.getParcelableExtra<UserDialist>("data")
        val hashmap = HashMap<String, UserDialist>()

        with(binding){

            var id = intent.getStringExtra("id")

            diaryTitle.hint = list!!.title
            diaryText.hint = list!!.diary

            backbtn.setOnClickListener {
                finish()
            }

            completebtn.setOnClickListener {
                val data = UserDialist(list!!.key, diaryTitle.text.toString(), diaryText.text.toString(), list!!.day)
                hashmap.put("${list!!.key}", data)
                diarydatabase.child(id!!).updateChildren(hashmap as Map<String, Any>)
                val intent = Intent(this@DiaryUpdate, Dialist::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }

    }

}