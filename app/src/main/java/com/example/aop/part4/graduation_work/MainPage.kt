package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.MainPageBinding
import android.content.Intent
import android.widget.Toast
import androidx.core.content.edit
import com.example.aop.part4.graduation_work.Board.ChatActivity
import com.example.aop.part4.graduation_work.Diaries.Dialist
import com.example.aop.part4.graduation_work.data.UserData

class MainPage : AppCompatActivity() {

    private lateinit var binding : MainPageBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("id", Context.MODE_PRIVATE)
        val sharedPreferences3 = getSharedPreferences("age", Context.MODE_PRIVATE)
        val sharedPreferences4 = getSharedPreferences("value", Context.MODE_PRIVATE)

        with(binding) {

            logo.setOnClickListener {
                var builder = AlertDialog.Builder(this@MainPage)
                builder.setTitle("개발자 정보")
                    .setMessage("동아대학교 컴퓨터공학과\n" +
                            "1724014 박준영\n" +
                            "1724535 이찬희\n" +
                            "1627374 이상혁\n")
                    .setPositiveButton("OK") { dialog, i ->
                        null
                    }
                    .create().show()
            }

            var name = intent.getParcelableExtra<UserData>("User")?.userName
            var id = intent.getParcelableExtra<UserData>("User")?.userId
            var value = intent.getParcelableExtra<UserData>("User")?.userValue
            var age = intent.getParcelableExtra<UserData>("User")?.userAge ?: 0

            if(name.isNullOrEmpty()) {
                name = sharedPreferences.getString("name", "") ?: ""
            } else {
                sharedPreferences.edit {
                    this.putString("name", name)
                    commit()
                }
            }

            if(id.isNullOrEmpty()){
                id = sharedPreferences2.getString("id", "") ?: ""
            } else{
                sharedPreferences2.edit {
                    this.putString("id", id)
                    commit()
                }
            }

            if(age == 0){
                age = sharedPreferences3.getInt("age", 0) ?: 0
            } else{
                sharedPreferences3.edit {
                    this.putInt("age", age)
                    commit()
                }
            }

            if(value.isNullOrEmpty()){
                value = sharedPreferences4.getString("value", "") ?: ""
            } else{
                sharedPreferences4.edit {
                    this.putString("value", value)
                    commit()
                }
            }
            Name.text = "$name" + "님, 반갑습니다."

            //로그아웃
            Logout.setOnClickListener {
                Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainPage, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            //우울증 항목 조사
            depressiveCheck.setOnClickListener {
                val intent = Intent(this@MainPage, DepressiveCheck::class.java)
                startActivity(intent)
                finish()
            }
            
            //운동 
            health.setOnClickListener {
                var intent = Intent(this@MainPage, HealthCheck::class.java)
                intent.putExtra("id", id)
                intent.putExtra("age", age)
                intent.putExtra("value", value)
                startActivity(intent)
            }
            
            //병원 찾기
            hospital.setOnClickListener {
                //val intent = Intent(this@MainPage, Hospital::class.java)
                //startActivity(intent)
                //finish()
            }
            
            //게시판
            board.setOnClickListener {
                val intent = Intent(this@MainPage, ChatActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("id", id)
                startActivity(intent)
            }
            
            //일기장
            diary.setOnClickListener {
                val intent = Intent(this@MainPage, Dialist::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                finish()
            }

            //기타 항목
            more.setOnClickListener {
                //val intent = Intent(this@MainPage, More::class.java)
                //startActivity(intent)
                //finish()
            }

            //베너 1
            Banner1.setOnClickListener {
                //
                //val intent = Intent(this@MainPage, Other::class.java)
                //startActivity(intent)
                //finish()
            }

            //베너2
            Banner2.setOnClickListener {
                //개인정보
                //val intent = Intent(this@MainPage, PersonalInfo::class.java)
                //startActivity(intent)
                //finish()
            }

            //베너3
            Banner3.setOnClickListener {
                //문의하기
                //val intent = Intent(this@MainPage, DeveloperInfo::class.java)
                //startActivity(intent)
                //finish()
            }
        }
    }
}