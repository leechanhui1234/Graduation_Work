package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.aop.part4.graduation_work.data.UserHealthCheck
import com.example.aop.part4.graduation_work.databinding.HealthViewBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.health_view.*

class HealthView : AppCompatActivity() {

    private lateinit var binding: HealthViewBinding
    private val database = Firebase.database.reference.child("Health_select")

    private var predata : String? = ""
    private var indata : String? = ""
    private var postdata : String? = ""

    private var value : String = ""     //성별
    private var age : Int = 0           //나이
    private var id : String = ""        //아이디

    private lateinit var chrono : Chronometer

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        age = intent.getIntExtra("age", 0)
        value = intent.getStringExtra("value") ?: ""
        id = intent.getStringExtra("id") ?: ""

        val sharedPreferences = getSharedPreferences("age", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("value", Context.MODE_PRIVATE)
        val sharedPreferences3 = getSharedPreferences("id", Context.MODE_PRIVATE)

        if (age == 0) {
            age = sharedPreferences.getInt("age", 0)
        } else {
            sharedPreferences.edit {
                this.putInt("age", age)
                commit()
            }
        }

        if (value.isNullOrEmpty()) {
            value = sharedPreferences2.getString("value", "") ?: ""
        } else {
            sharedPreferences2.edit {
                this.putString("value", value)
                commit()
            }
        }

        if (id.isNullOrEmpty()) {
            id = sharedPreferences3.getString("id", "") ?: ""
        } else {
            sharedPreferences3.edit {
                this.putString("id", id)
                commit()
            }
        }

        if (intent.getStringExtra("predata") != null) {
            //운동 추천에서 넘어왔을 경우
            predata = intent.getStringExtra("predata")
            indata = intent.getStringExtra("indata")
            postdata = intent.getStringExtra("postdata")
        }
        else {
            //DB에서 가져오기
            if (true) {
                //DB 있음
                Toast.makeText(applicationContext, "가장 최근에 추천된 운동으로 세팅합니다.", Toast.LENGTH_SHORT).show()

            }

            else {
                //DB 없음
                Toast.makeText(applicationContext, "저장된 운동이 없습니다. 추천 시스템으로 넘어갑니다.", Toast.LENGTH_SHORT).show()

                //운동 추천
                var intent = Intent(this@HealthView, HealthCheck::class.java)
                intent.putExtra("id", id)
                intent.putExtra("age", age)
                intent.putExtra("value", value)
                startActivity(intent)
                finish()
            }
        }

        with(binding) {
            /*pre_health.setText(predata)
            in_health.setText(indata)
            post_health.setText(postdata)*/

            //이후로 운동에 필요한 기능들 구현 필요.

            //총 운동시간 측정용
            chrono.base = SystemClock.elapsedRealtime()
            chrono.start()      //측정 시작
            
            //운동 보여줄 페이지 구성
            //https://todaycode.tistory.com/27

        }
    }

    private fun controlDatabase(id : String) {
        database?.child(id!!).addChildEventListener(object: ChildEventListener {
            //DB 가져오기
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val getItem = snapshot.getValue(UserHealthCheck::class.java)
                val getKey = snapshot.key

                val data = UserHealthCheck(getKey!!, getItem!!.date, getItem!!.pre_select, getItem!!.in_select, getItem!!.post_select)

                if (data != null) {
                    /*pre_health.setText(data.pre_select)
                    in_health.setText(data.in_select)
                    post_health.setText(data.post_select)*/
                }
            }

            //수정
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            //삭제
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}