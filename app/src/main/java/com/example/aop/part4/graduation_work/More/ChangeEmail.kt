package com.example.aop.part4.graduation_work.More

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.aop.part4.graduation_work.LoginActivity
import com.example.aop.part4.graduation_work.R
import com.example.aop.part4.graduation_work.Request.ChangeEmailRequest
import com.example.aop.part4.graduation_work.Request.ChangePwRequest
import com.example.aop.part4.graduation_work.databinding.EmailChangeBinding
import com.example.aop.part4.graduation_work.databinding.PwChangeBinding
import org.json.JSONObject

class ChangeEmail: AppCompatActivity() {

    private lateinit var binding: EmailChangeBinding
    private var email: String ?= null
    private var id: String ?= null
    private var beforeemail: String ?= null
    private var afteremail: String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmailChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("email")
        id = intent.getStringExtra("id")

        binding.emailchangebtn.setOnClickListener {
            checkemail()
        }
    }

    private fun checkemail() {
        beforeemail = binding.beforeemail.text.toString()
        afteremail = binding.afteremail.text.toString()

        if(beforeemail.isNullOrEmpty() || afteremail.isNullOrEmpty()){
            Toast.makeText(this@ChangeEmail, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        else if(email != beforeemail){
            Toast.makeText(this@ChangeEmail, "현재 이메일이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        else if(beforeemail == afteremail){
            Toast.makeText(this@ChangeEmail, "현재 이메일과 새 이메일이 일치합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        else {
            UpdateDatabase(id!!, afteremail!!)
        }
    }

    private fun UpdateDatabase(id: String, email: String) {
        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                if(success) {
                    Toast.makeText(applicationContext, "이메일을 변경하였습니다. 다시 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ChangeEmail, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "예상치 못한 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    return@Listener
                }
            } catch(e: Exception) {
                e.printStackTrace()
                Log.d("ChangeEmail", e.message.toString())
                Toast.makeText(this, "예상치 못한 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val registerRequest =
            ChangeEmailRequest(id, email, responseListener)
        val queue = Volley.newRequestQueue(this@ChangeEmail)
        queue.add(registerRequest)
    }
}