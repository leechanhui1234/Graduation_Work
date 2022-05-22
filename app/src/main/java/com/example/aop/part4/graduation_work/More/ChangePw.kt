package com.example.aop.part4.graduation_work.More

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.aop.part4.graduation_work.Board.ChatActivity
import com.example.aop.part4.graduation_work.LoginActivity
import com.example.aop.part4.graduation_work.R
import com.example.aop.part4.graduation_work.Request.ChangePwRequest
import com.example.aop.part4.graduation_work.Request.RegisterRequest
import com.example.aop.part4.graduation_work.databinding.ActivityLoginBinding
import com.example.aop.part4.graduation_work.databinding.MainPageBinding
import com.example.aop.part4.graduation_work.databinding.PwChangeBinding
import org.json.JSONObject

class ChangePw: AppCompatActivity(){

    private lateinit var binding: PwChangeBinding
    var pw: String ?= null
    var id: String ?= null
    var beforepw: String ?= null
    var afterpw: String ?= null
    var afterpwcheck: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PwChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pw = intent.getStringExtra("pw")
        id = intent.getStringExtra("id")

        binding.pwchangebtn.setOnClickListener {
            checkpw()
        }
    }

    private fun checkpw() {
        beforepw = binding.beforepw.text.toString()
        afterpw = binding.afterpw.text.toString()
        afterpwcheck = binding.afterpwcheck.text.toString()

        if(beforepw.isNullOrEmpty() || afterpw.isNullOrEmpty() || afterpwcheck.isNullOrEmpty()){
            Toast.makeText(this@ChangePw, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        else if(pw != beforepw){
            Toast.makeText(this@ChangePw, "현재 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        else if(afterpw != afterpwcheck){
            Toast.makeText(this@ChangePw, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        else if(beforepw == afterpw){
            Toast.makeText(this@ChangePw, "현재 비밀번호와 새 비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        else {
            var regex1 = Regex("""[A-Z]""") //비밀번호 대문자
            var regex2 = Regex("""[~|!|@|#|$|%|^|&|*|(|)|_|+|/|.|`|<|>|?]""") //비밀번호 특수문자

            if (!regex1.containsMatchIn(afterpw!!)) {
                Toast.makeText(this@ChangePw, "비밀번호에 대문자가 포함되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else if (!regex2.containsMatchIn(afterpw!!)) {
                Toast.makeText(this@ChangePw, "비밀번호에 특수문자가 포함되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else if (afterpw!!.length < 8) {
                Toast.makeText(this@ChangePw, "8자 이상의 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return
            }
            else {
                UpdateDatabase(id!!, afterpw!!)
            }
        }
    }

    private fun UpdateDatabase(id: String, pw: String){
        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                if(success) {
                    Toast.makeText(applicationContext, "비밀번호를 변경하였습니다. 다시 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ChangePw, LoginActivity::class.java)
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
                Log.d("ChangePw", e.message.toString())
                Toast.makeText(this, "예상치 못한 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val registerRequest =
            ChangePwRequest(id, pw, responseListener)
        val queue = Volley.newRequestQueue(this@ChangePw)
        queue.add(registerRequest)
    }
}