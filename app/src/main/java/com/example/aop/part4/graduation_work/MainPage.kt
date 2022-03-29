package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.MainPageBinding
import com.example.aop.part4.graduation_work.databinding.NewAccountBinding
import android.content.Intent
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.view.KeyEventDispatcher
import com.example.aop.part4.graduation_work.data.UserData

class MainPage : AppCompatActivity() {

    private lateinit var binding : MainPageBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)


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

            if(name.isNullOrEmpty()){
                name = sharedPreferences.getString("name", "") ?: ""
            } else {
                sharedPreferences.edit {
                    this.putString("name", name)
                    commit()
                }
            }
            Name.text = "$name" + "님, 반갑습니다."

            Logout.setOnClickListener {
                //로그아웃 기능 구현할 것.
                Toast.makeText(applicationContext, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainPage, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            depressiveCheck.setOnClickListener {
                val intent = Intent(this@MainPage, DepressiveCheck::class.java)
                startActivity(intent)
                finish()
            }
            
            health.setOnClickListener {
                //val intent = Intent(this@MainPage,Health::class.java)
                //startActivity(intent)
                //finish()
            }
            
            hospital.setOnClickListener {
                //val intent = Intent(this@MainPage,Hospital::class.java)
                //startActivity(intent)
                //finish()
            }
            
            board.setOnClickListener {
                //val intent = Intent(this@MainPage,Board::class.java)
                //startActivity(intent)
                //finish()
            }
            
            diary.setOnClickListener {
                val intent = Intent(this@MainPage, Diary::class.java)
                startActivity(intent)
                finish()
            }

            other.setOnClickListener {
                //val intent = Intent(this@MainPage, Other::class.java)
                //startActivity(intent)
                //finish()
            }
        }
    }
}