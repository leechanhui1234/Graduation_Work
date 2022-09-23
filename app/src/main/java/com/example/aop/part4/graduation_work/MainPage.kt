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
import com.example.aop.part4.graduation_work.Hospital.HospitalList
import com.example.aop.part4.graduation_work.More.MorePage
import com.example.aop.part4.graduation_work.data.UserData
import kotlinx.android.synthetic.main.custom_health.view.*

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

            var data = intent.getParcelableExtra<UserData>("User")
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

            depressiveCheck.clipToOutline = true
            health.clipToOutline = true
            hospital.clipToOutline = true
            board.clipToOutline = true
            diary.clipToOutline = true
            more.clipToOutline = true

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
            }
            
            //운동
            health.setOnClickListener {
                val dialog = AlertDialog.Builder(this@MainPage)
                val inflater = layoutInflater
                val customView = inflater.inflate(R.layout.custom_health, null)

                dialog.setView(customView)

                customView.health_recommend.setOnClickListener {
                    var intent = Intent(this@MainPage, HealthCheck::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("age", age)
                    intent.putExtra("value", value)
                    startActivity(intent)
                }

                customView.health_start.setOnClickListener {
                    var intent = Intent(this@MainPage, HealthView::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("age", age)
                    intent.putExtra("value", value)
                    startActivity(intent)
                }

                dialog.show()
            }
            
            //병원 찾기
            hospital.setOnClickListener {
                val intent = Intent(this@MainPage, HospitalList::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
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
            }

            //기타 항목
            more.setOnClickListener {
                val intent = Intent(this@MainPage, MorePage::class.java)
                intent.putExtra("data", data)
                startActivity(intent)
            }
        }
    }
}