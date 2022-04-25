package com.example.aop.part4.graduation_work.Board

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.Board.model.ChatModel
import com.example.aop.part4.graduation_work.databinding.ChatWriteBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WriteActivity: AppCompatActivity() {

    private lateinit var binding: ChatWriteBinding

    private val database = Firebase.database.reference.child("board")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChatWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id = intent.getStringExtra("id")

        bindViews(id!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindViews(id: String) {
        with(binding){
            backbtn.setOnClickListener {
                finish()
            }

            completebtn.setOnClickListener {
                if (title.text.isNotEmpty() && content.text.isNotEmpty()){
                    val str1 = title.text.toString()
                    val str2 = content.text.toString()
                    val now = LocalDate.now()
                    var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                    Toast.makeText(this@WriteActivity, id, Toast.LENGTH_SHORT).show()

                    val model = ChatModel(id, str1, str2, Strnow.toString())

                    database.push().setValue(model)

                    finish()
                } else {
                    Toast.makeText(this@WriteActivity, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}