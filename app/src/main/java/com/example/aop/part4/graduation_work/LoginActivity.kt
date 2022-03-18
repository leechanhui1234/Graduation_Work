package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.aop.part4.graduation_work.Request.LoginRequest
import com.example.aop.part4.graduation_work.data.UserData
import com.example.aop.part4.graduation_work.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var sharePreferences: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()

        sharePreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharePreferences.edit()

        checkSharedPreference()
    }

    private fun bindViews() {
        binding.Login.setOnClickListener {
            if (binding.AutoLogin.isChecked) {
                with(binding){
                    editor.putBoolean(getString(R.string.auto_login), true)
                    editor.apply()
                    editor.putString(getString(R.string.prompt_ID), ID.text.toString())
                    editor.putString(getString(R.string.prompt_PW), PW.text.toString())
                    editor.commit()
                }
            }
            else {
                with(binding){
                    editor.putBoolean(getString(R.string.auto_login), false)
                    editor.putString(getString(R.string.prompt_ID), ID.text.toString())
                    editor.putString(getString(R.string.prompt_PW), PW.text.toString())
                    editor.commit()
                }
            }

            val id = binding.ID.text.toString()
            val pw = binding.PW.text.toString()
            checkUser(id, pw)
        }

        binding.NewAccount.setOnClickListener {
            val intent = Intent(this, NewAccount::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkUser(userId: String, userPw: String) {
        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                if(success){
                    val userId = jsonObject.getString("userId")
                    val userPw = jsonObject.getString("userPw")
                    val userName = jsonObject.getString("userName")
                    val userEmail = jsonObject.getString("userEmail")
                    val userAge = jsonObject.getInt("userAge")
                    val userValue = jsonObject.getString("userValue")
                    val data = UserData(userId, userPw, userEmail, userAge, userValue, userName)

                    //todo intent를 이용해 메인 화면으로 보내버리자
                    Toast.makeText(applicationContext, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "잘못된 아이디 or 패스워드 값입니다. 다시 로그인해주세요", Toast.LENGTH_SHORT).show()
                    binding.ID.setText("")
                    binding.PW.setText("")
                    return@Listener
                }
            } catch(e: Exception){
                e.printStackTrace()
                Log.d("MainActivity", e.message.toString())
                Toast.makeText(this, "예상치 못한 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val loginRequest =
            LoginRequest(userId, userPw, responseListener)
        val queue = Volley.newRequestQueue(this@LoginActivity)
        queue.add(loginRequest)
    }

    @SuppressLint("StringFormatInvalid")
    private fun checkSharedPreference() {
        with(binding){
            AutoLogin.isChecked = sharePreferences.getBoolean(getString(R.string.auto_login), false)
            ID.setText(sharePreferences.getString(getString(R.string.prompt_ID), ""))
            PW.setText(sharePreferences.getString(getString(R.string.prompt_PW), ""))
        }
    }
}
