package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.aop.part4.graduation_work.databinding.HealthBinding
import kotlinx.android.synthetic.main.health.*

class Health : AppCompatActivity() {

    private lateinit var binding: HealthBinding

    private var id : String = ""        //아이디
    private var value : String = ""     //성별
    private var age : Int = 0           //나이

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id") ?: ""
        age = intent.getIntExtra("age", 0)
        value = intent.getStringExtra("value") ?: ""

        val sharedPreferences3 = getSharedPreferences("id", Context.MODE_PRIVATE)
        val sharedPreferences = getSharedPreferences("age", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("value", Context.MODE_PRIVATE)

        if (id.isNullOrEmpty()) {
            id = sharedPreferences3.getString("id", "") ?: ""
        } else {
            sharedPreferences3.edit {
                this.putString("id", id)
                commit()
            }
        }

        if (age == 0) {
            age = sharedPreferences.getInt("age", 0)
        } else {
            sharedPreferences.edit {
                this.putInt("age", age)
                commit()
            }
        }

        if (value.isNullOrEmpty()) {
            value = sharedPreferences2.getString("value", "") ?: ""
        } else {
            sharedPreferences2.edit {
                this.putString("value", value)
                commit()
            }
        }

        with(binding) {
            
            health_recommend.setOnClickListener {
                //운동 추천
                var intent = Intent(this@Health, HealthCheck::class.java)
                intent.putExtra("id", id)
                intent.putExtra("age", age)
                intent.putExtra("value", value)
                startActivity(intent)
            }
            
            health_start.setOnClickListener {
                //운동 하기
                var intent = Intent(this@Health, HealthView::class.java)
                intent.putExtra("id", id)
                intent.putExtra("age", age)
                intent.putExtra("value", value)
                startActivity(intent)
            }

            my_health.setOnClickListener {
                //내 기록 보기
                var intent = Intent(this@Health, HealthRecord::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                finish()
            }

        }

    }
}