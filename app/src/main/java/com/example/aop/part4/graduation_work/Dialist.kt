package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.aop.part4.graduation_work.databinding.DialistBinding

class Dailist : AppCompatActivity() {

    private lateinit var binding : DialistBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            NeWrite.setOnClickListener {
                val intent = Intent(this@Dailist, Diary::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}