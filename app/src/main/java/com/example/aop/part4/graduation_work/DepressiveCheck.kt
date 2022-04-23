package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.DepressiveCheckBinding

class DepressiveCheck : AppCompatActivity(){
    private lateinit var binding: DepressiveCheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent1 = Intent(this, MainPage::class.java)
        val intent2 = Intent(this, Ces::class.java)
        val intent3 = Intent(this, Depression::class.java)
        val intent4 = Intent(this, Old::class.java)
        super.onCreate(savedInstanceState)
        binding = DepressiveCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            backbtn.setOnClickListener {
                startActivity(intent1)
                finish()
            }
            cesCheck.setOnClickListener{
                startActivity(intent2)
                finish()
            }
            depCheck.setOnClickListener{
                startActivity(intent3)
                finish()
            }
            oldCheck.setOnClickListener{
                startActivity(intent4)
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