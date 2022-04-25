package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.HealthBinding

class Health : AppCompatActivity() {

    private lateinit var binding: HealthBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

        }

    }
}