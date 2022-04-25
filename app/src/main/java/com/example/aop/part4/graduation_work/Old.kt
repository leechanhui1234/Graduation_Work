package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.OldDepCheckBinding

class Old : AppCompatActivity(){

    private lateinit var binding: OldDepCheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        val intent1 = Intent(this, DepressiveCheck::class.java)
        val intent2 = Intent(this, OldResult::class.java)
        super.onCreate(savedInstanceState)
        binding = OldDepCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){


            backbtn.setOnClickListener {
                startActivity(intent1)
                finish()
            }
            completebtn.setOnClickListener{
                startActivity(intent2)
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