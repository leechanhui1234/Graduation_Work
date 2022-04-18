package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.aop.part4.graduation_work.Board.ChatActivity
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.data.UserDialist
import com.example.aop.part4.graduation_work.databinding.DiaryUpdateBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DiaryUpdate : AppCompatActivity() {

    private lateinit var binding: DiaryUpdateBinding
    private val diarydatabase = Firebase.database.reference.child("diary")

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val list = intent.getParcelableExtra<UserDialist>("data")
        val hashmap = HashMap<String, UserDialist>()

        with(binding){
            backbtn.setOnClickListener {
                finish()
            }

            completebtn.setOnClickListener {
                val data = UserDialist(list!!.key, diaryTitle.text.toString(), diaryText.text.toString(), list!!.day)
                hashmap.put("${list!!.key}", data)
                diarydatabase.updateChildren(hashmap as Map<String, Any>)
                val intent = Intent(this@DiaryUpdate, Dialist::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }

    }

}