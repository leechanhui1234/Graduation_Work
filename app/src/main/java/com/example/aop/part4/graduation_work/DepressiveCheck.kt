package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.DepressiveCheckBinding

class DepressiveCheck : AppCompatActivity(){
    private lateinit var binding: DepressiveCheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DepressiveCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
        finish()
    }
}