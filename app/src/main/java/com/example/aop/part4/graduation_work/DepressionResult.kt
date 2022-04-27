package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.DepressiveResultBinding

class DepressionResult : AppCompatActivity(){

    private lateinit var binding: DepressiveResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DepressiveResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var score = intent.getIntExtra("score", 0)

        with(binding){

            result.text = score.toString()
            backbtn.setOnClickListener {
                val intent1 = Intent(this@DepressionResult, MainPage::class.java)
                startActivity(intent1)
                finish()
            }

            resultcheck.setOnClickListener {
                val intent1 = Intent(this@DepressionResult, MainPage::class.java)
                startActivity(intent1)
                finish()
            }
        }
    }
}