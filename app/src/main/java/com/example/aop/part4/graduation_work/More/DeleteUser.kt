package com.example.aop.part4.graduation_work.More

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.aop.part4.graduation_work.LoginActivity
import com.example.aop.part4.graduation_work.Request.ChangeEmailRequest
import com.example.aop.part4.graduation_work.Request.DeleteUserRequest
import com.example.aop.part4.graduation_work.databinding.DeleteUserBinding
import com.example.aop.part4.graduation_work.databinding.PwChangeBinding
import org.json.JSONObject

class DeleteUser: AppCompatActivity() {
    private lateinit var binding: DeleteUserBinding
    var pw: String ?= null
    var id: String ?= null
    var currentid: String ?= null
    var currentpw: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DeleteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pw = intent.getStringExtra("pw")
        id = intent.getStringExtra("id")

        binding.deleteuserbtn.setOnClickListener {
            checkinfo()
        }
    }

    private fun checkinfo(){
        currentid = binding.currentid.text.toString()
        currentpw = binding.currentpw.text.toString()

        if(currentid.isNullOrEmpty() || currentpw.isNullOrEmpty()){
            Toast.makeText(this@DeleteUser, "아이디 혹은 비밀번호를 입력하지 않으셨습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        else if(id != currentid){
            Toast.makeText(this@DeleteUser, "아이디가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        else if(pw != currentpw){
            Toast.makeText(this@DeleteUser, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        else {
            DeleteDatabase()
        }
    }

    private fun DeleteDatabase(){
        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                if(success) {
                    Toast.makeText(applicationContext, "회원탈퇴처리가 완료되었습니다. 다시 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@DeleteUser, LoginActivity::class.java)
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
                Log.d("DeleteUser", e.message.toString())
                Toast.makeText(this, "예상치 못한 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val registerRequest =
            DeleteUserRequest(id!!, pw!!, responseListener)
        val queue = Volley.newRequestQueue(this@DeleteUser)
        queue.add(registerRequest)
    }
}