package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.aop.part4.graduation_work.Healths.HealthAdapter
import com.example.aop.part4.graduation_work.Healths.database.Appdatabase
import com.example.aop.part4.graduation_work.Healths.database.getDatabase
import com.example.aop.part4.graduation_work.data.UserHealthCheck
import com.example.aop.part4.graduation_work.data.UserHealthInfo
import com.example.aop.part4.graduation_work.databinding.HealthViewBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

class HealthView : AppCompatActivity() {
    lateinit var db: Appdatabase
    private lateinit var binding: HealthViewBinding
    private val database = Firebase.database.reference.child("Health_select")

    private var predata : String? = ""
    private var indata : String? = ""
    private var postdata : String? = ""

    private var value : String = ""     //성별
    private var age : Int = 0           //나이
    private var id : String = ""        //아이디
    var list = emptyList<String>()
    private lateinit var viewPager: ViewPager2

    lateinit var Date : LocalDate   //현재 날짜

    private val Num = 3   //운동을 보여줄 페이지 수

    //private lateinit var chrono : Chronometer

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = getDatabase(this)

        age = intent.getIntExtra("age", 0)
        value = intent.getStringExtra("value") ?: ""
        id = intent.getStringExtra("id") ?: ""
        viewPager = binding.healthView

        Date = LocalDate.now()

        val sharedPreferences = getSharedPreferences("age", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("value", Context.MODE_PRIVATE)
        val sharedPreferences3 = getSharedPreferences("id", Context.MODE_PRIVATE)

        //sharedPreferences 나이, 성별, 아이디 저장
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

        //추천된 운동 보여주기
        if (intent.getStringExtra("predata") != null) {
            //운동 추천에서 넘어왔을 경우
            predata = intent.getStringExtra("predata")
            indata = intent.getStringExtra("indata")
            postdata = intent.getStringExtra("postdata")

            GlobalScope.launch(Dispatchers.IO) {
                var data = db.userHealthDao().getData(id)
                if (data == null) {
                    db.userHealthDao().insertData(UserHealthInfo(null, id, predata, indata, postdata, Date.toString(), 0))
                } else {
                    db.userHealthDao().updateData(UserHealthInfo(data.uid, id, predata, indata, postdata, Date.toString(),0))
                }
            }
        }
        //운동 보여주기로 바로 들어왔을 경우(추천된 운동 없음.)
        else {
            //DB에서 가져오기
            GlobalScope.launch(Dispatchers.IO) {
                var data = db.userHealthDao().getData(id)

                runOnUiThread {
                    if (data != null) {
                        list = emptyList()
                        //DB 있음
                        Toast.makeText(applicationContext, "${data.toString()}", Toast.LENGTH_SHORT).show()
                        list = list + data.pre_select!!
                        list = list + data.in_select!!
                        list = list + data.post_select!!
                        displayData()
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
            }

        }

        with(binding) {
            /*pre_health.setText(predata)
            in_health.setText(indata)
            post_health.setText(postdata)*/

            //이후로 운동에 필요한 기능들 구현 필요.

            //총 운동시간 측정용
            //chrono.base = SystemClock.elapsedRealtime()
            //chrono.start()      //측정 시작
            
            //운동 보여줄 페이지 구성
            //https://todaycode.tistory.com/27

            //controlDatabase(id)

        }
    }

    private fun displayData() {
        var adapter = HealthAdapter(list)
        viewPager.adapter = adapter
    }

    /*private fun controlDatabase(id : String) {
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
    }*/

    /*class ScreenView : FragmentActivity() {
    class ScreenView : FragmentActivity() {
        private val Num = 3
        private lateinit var viewPager : ViewPager2

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.health_page1)

            viewPager = findViewById(R.id.pager)
        }

        override fun onBackPressed() {
            if (viewPager.currentItem == 0) {
                super.onBackPressed()
            }
            else {
                viewPager.currentItem = viewPager.currentItem - 1
            }
        }

        private inner class ViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
            override fun getItemCount() : Int = Num
            override fun createFragment(position : Int) : Fragment = ViewPagerFragment1()
        }
    }*/
}
