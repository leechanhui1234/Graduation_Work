package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.CesDResultBinding

class DepressionResult : AppCompatActivity(){

    private lateinit var binding: CesDResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {


        val intent1 = Intent(this, MainPage::class.java)
        val intent2 = Intent(this, DepressiveCheck::class.java)

        var score = intent.getIntExtra("score",0)
        super.onCreate(savedInstanceState)
        binding = CesDResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){

            result.setText(score.toString())
            backbtn.setOnClickListener {
                startActivity(intent1)
                finish()
            }

        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
        finish()
    }

}