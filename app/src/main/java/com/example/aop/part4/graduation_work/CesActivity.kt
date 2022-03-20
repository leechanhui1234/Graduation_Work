package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.CesDDepressionDiagnosisBinding


class CesActivity : AppCompatActivity(){
    private lateinit var binding: CesDDepressionDiagnosisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CesDDepressionDiagnosisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toDiagnosis1.setOnClickListener{
            val intent = Intent(this, Ces1Activity::class.java)
            startActivity(intent)
        }
    }

}