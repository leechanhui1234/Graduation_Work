package com.example.aop.part4.graduation_work.Board

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.Board.model.ChatModel
import com.example.aop.part4.graduation_work.databinding.ActivityWriteBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WriteActivity: AppCompatActivity() {

    private lateinit var binding: ActivityWriteBinding

    private val database = Firebase.database.reference.child("board")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.getStringExtra("name")

        bindViews(name!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindViews(name: String) {
        with(binding){
            backbtn.setOnClickListener {
                finish()
            }

            completebtn.setOnClickListener {
                if(title.text.isNotEmpty() && content.text.isNotEmpty()){
                    val str1 = title.text.toString()
                    val str2 = content.text.toString()
                    val now = LocalDate.now()
                    var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                    val model = ChatModel(name, str1, str2, Strnow.toString())

                    database.push().setValue(model)

                    finish()
                }else{
                    Toast.makeText(this@WriteActivity, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}