package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.aop.part4.graduation_work.databinding.DepressiveResultBinding

class DepressionResult : AppCompatActivity(){

    private lateinit var binding: DepressiveResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DepressiveResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var score = intent.getIntExtra("score",0)

        with(binding){
            /*depression1.isInvisible
            depression2.isInvisible
            depression3.isInvisible*/
            result.setText(score.toString())
            if(score in 0..20) {
                depression1.visibility = android.view.View.VISIBLE
                depressionText1.visibility = android.view.View.VISIBLE
            }
            if(score in 21..24) {
                depression2.visibility = android.view.View.VISIBLE
                depressionText2.visibility = android.view.View.VISIBLE
            }
            if(score in 25..60) {
                depression3.visibility = android.view.View.VISIBLE
                depressionText3.visibility = android.view.View.VISIBLE
            }
            backbtn.setOnClickListener {
                //Toast.makeText(applicationContext,score.toString(),Toast.LENGTH_SHORT).show()
                finish()
            }

            resultcheck.setOnClickListener {
                val intent1 = Intent(this@DepressionResult, MainPage::class.java)
               intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent1)
                finish()
                //Toast.makeText(applicationContext,"토스트메세지",Toast.LENGTH_SHORT).show()

            }
        }
    }
}