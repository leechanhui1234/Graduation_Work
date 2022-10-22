package com.example.aop.part4.graduation_work.Diaries

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.R
import com.example.aop.part4.graduation_work.data.UserDiary
import com.example.aop.part4.graduation_work.databinding.DiaryBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.calandar.*
import java.lang.String.format
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Diary: AppCompatActivity() {

    private lateinit var binding : DiaryBinding
    var Year = 0
    var Month = 0
    var Day = 0


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var currenttime = Calendar.getInstance().time
        Year = Integer.parseInt(SimpleDateFormat("yyyy", Locale.getDefault()).format(currenttime))
        Month = Integer.parseInt(SimpleDateFormat("MM", Locale.getDefault()).format(currenttime))
        Day = Integer.parseInt(SimpleDateFormat("dd", Locale.getDefault()).format(currenttime))

        with(binding) {

            var database = Firebase.database.reference.child("diary")

            year.text = Year.toString()
            month.text = Month.toString()
            day.text = Day.toString()

            //Toast.makeText(this@Diary, text.toString(), Toast.LENGTH_SHORT).show()

            day.setOnClickListener {
                alertCalandar()
            }

            month.setOnClickListener {
                alertCalandar()
            }

            year.setOnClickListener {
                alertCalandar()
            }
            
            //dialist로 돌아가기
            diarySave.setOnClickListener {
                var Date = LocalDate.of(Year, Month, Day)
                var text = Date.format(DateTimeFormatter.ofPattern("yyyy. MM. dd(EE)"))
                var id = intent.getStringExtra("id")
                database.child(id!!).push().setValue(UserDiary(diaryTitle.text.toString(), diaryText.text.toString(), text.toString()))
                Toast.makeText(applicationContext, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
                //저장 후 리스트로 돌아가기
                finish()
            }

            backbtn.setOnClickListener {
                finish()
            }
        }
    }

    private fun alertCalandar() {
        val dialog = AlertDialog.Builder(this@Diary)
        val inflater = layoutInflater
        val customView = inflater.inflate(R.layout.calandar, null)
        var calendar = customView.findViewById<CalendarView>(R.id.calender)

        calendar.setOnDateChangeListener { calendarView, year, month, day ->
            Year = year
            Month = month + 1
            Day = day
        }

        dialog.setTitle("날짜 선택")
            .setPositiveButton("확인") { _, _ ->
                with(binding) {
                    year.text = Year.toString()
                    month.text = Month.toString()
                    day.text = Day.toString()
                }
            }
            .setView(customView)
            .show()
    }

    override fun onBackPressed() {
        finish()
    }
}
