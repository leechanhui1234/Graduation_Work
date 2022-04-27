package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.HealthViewBinding
import kotlinx.android.synthetic.main.health_view.*

class HealthView : AppCompatActivity() {

    private lateinit var binding: HealthViewBinding

    private var predata : String? = ""
    private var indata : String? = ""
    private var postdata : String? = ""

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        predata = intent.getStringExtra("predata")
        indata = intent.getStringExtra("indata")
        postdata = intent.getStringExtra("postdata")

        with(binding) {
            pre_health.setText(predata)
            in_health.setText(indata)
            post_health.setText(postdata)

            //이후로 운동에 필요한 기능들 구현 필요.
        }

    }
}