package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.CesDDepressionDiagnosis1Binding


class Ces1Activity : AppCompatActivity(){
    private  lateinit var binding: CesDDepressionDiagnosis1Binding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = CesDDepressionDiagnosis1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}