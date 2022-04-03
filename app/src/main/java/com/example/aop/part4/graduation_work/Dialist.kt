package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.aop.part4.graduation_work.databinding.DialistBinding

class Dialist : AppCompatActivity() {

    private lateinit var binding : DialistBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            Delete.setOnClickListener {
                //삭제
            }

            NeWrite.setOnClickListener {
                //글쓰기
                val intent = Intent(this@Dialist, Diary::class.java)
                startActivity(intent)
                finish()
            }

            reWrite.setOnClickListener {
                //수정하기
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
        finish()
    }
}