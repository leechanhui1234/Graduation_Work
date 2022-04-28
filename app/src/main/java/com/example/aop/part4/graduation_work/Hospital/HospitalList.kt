package com.example.aop.part4.graduation_work.Hospital

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.HospitalListBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HospitalList: AppCompatActivity() {
    private val scope = MainScope()
    private lateinit var binding: HospitalListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HospitalListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchKeyword()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun searchKeyword(){
        scope.launch {
            val data = Repository.getListApi("129.034123" ,"35.142898", "정신병원")

            Log.e("test", data.toString())
        }
    }
}