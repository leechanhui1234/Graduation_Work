package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.viewpager2.widget.ViewPager2
import com.example.aop.part4.graduation_work.Healths.HealthAdapter
import com.example.aop.part4.graduation_work.Healths.database.Appdatabase
import com.example.aop.part4.graduation_work.Healths.database.getDatabase
import com.example.aop.part4.graduation_work.data.UserHealthInfo
import com.example.aop.part4.graduation_work.databinding.HealthViewBinding
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

    private var preurl: String? = ""
    private var inurl: String? = ""
    private var posturl: String? = ""

    private var value : String = ""     //성별
    private var age : Int = 0           //나이
    private var id : String = ""        //아이디
    var list = emptyList<String>()
    var urllist = emptyList<String>()
    private lateinit var viewPager: ViewPager2

    lateinit var Date : LocalDate   //현재 날짜

    private lateinit var chrono : Chronometer
    var CountDown : CountDownTimer? = null

    @RequiresApi(Build.VERSION_CODES.O)
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
            preurl = intent.getStringExtra("preurl")
            inurl = intent.getStringExtra("inurl")
            posturl = intent.getStringExtra("posturl")

            GlobalScope.launch(Dispatchers.IO) {
                var data = db.userHealthDao().getData(id)
                if (data == null) {
                    db.userHealthDao().insertData(UserHealthInfo(null, id, predata, indata, postdata, preurl, inurl, posturl, Date.toString(), 0))
                } else {
                    db.userHealthDao().updateData(UserHealthInfo(data.uid, id, predata, indata, postdata, preurl, inurl, posturl, Date.toString(),0))
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
                        urllist = emptyList()
                        //DB 있음
                        Toast.makeText(applicationContext, "${data.in_url} ${data.post_url}", Toast.LENGTH_SHORT).show()
                        list = list + data.pre_select!!
                        list = list + data.in_select!!
                        list = list + data.post_select!!
                        urllist = urllist + data.pre_url!!.split("v=")[1]
                        urllist = urllist + data.in_url!!.split("v=")[1]
                        urllist = urllist + data.post_url!!.split("v=")[1]
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

            //총 운동시간 측정용
            chrono = chronometer
            var pauseTimeCounter = 0L
            var pauseTime = 0L
            
            //운동 시작
            starting.setOnClickListener {

                CountDown = object : CountDownTimer( (10 - pauseTimeCounter) * 1000, 1000) {
                    @SuppressLint("SetTextI18n")
                    override fun onTick(millisUntilFinished: Long) {
                        val second = millisUntilFinished / 1000
                        val minute = second / 60
                        val seconds = second % 60
                        timer.text = "$minute : $seconds"
                        pauseTimeCounter++
                    }

                    override fun onFinish() {
                        var dialog = AlertDialog.Builder(this@HealthView)
                        dialog.setTitle("운동 종료!")
                        dialog.setMessage("5분 휴식 후 다음 운동으로 넘어가세요!")
                        dialog.setPositiveButton("확인", null)

                        pauseTimeCounter = 0
                        pauseTime = chrono.base - SystemClock.elapsedRealtime()
                        chrono.stop()

                        dialog.show()
                    }
                }.start()

                chrono.base = SystemClock.elapsedRealtime() + pauseTime + 1
                chrono.start()      //측정 시작

            }

            pausing.setOnClickListener {
                CountDown?.cancel()
                pauseTime = chrono.base - SystemClock.elapsedRealtime()
                chrono.stop()
            }

            //운동 종료
            ending.setOnClickListener {
                var dialog = AlertDialog.Builder(this@HealthView)
                dialog.setTitle("운동 종료")
                dialog.setMessage("운동을 종료하시겠습니까?")

                dialog.setPositiveButton("확인") { dialog, which ->
                    var dialog1 = AlertDialog.Builder(this@HealthView)
                    dialog1.setMessage("운동이 종료되었습니다.")
                    dialog1.setPositiveButton("확인", null)

                    CountDown?.cancel()
                    CountDown = null
                    pauseTimeCounter = 0

                    chrono.stop()
                    pauseTime = 0L

                    dialog1.show()
                }

                dialog.setNegativeButton("취소") { dialog, which ->
                    var dialog1 = AlertDialog.Builder(this@HealthView)
                    dialog1.setMessage("취소되었습니다.")
                }

                dialog.show()
            }

        }

        val backbtn = findViewById<ImageButton>(R.id.backbtn)
        backbtn.setOnClickListener {
            val intent1 = Intent(this@HealthView, MainPage::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            finish()
        }
    }

    private fun displayData() {
        var adapter = HealthAdapter(list, urllist)
        viewPager.adapter = adapter
    }
}
