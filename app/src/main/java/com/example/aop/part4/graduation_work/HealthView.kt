package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.HealthViewBinding

class HealthView : AppCompatActivity() {

    private lateinit var binding: HealthViewBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

        }

    }
}