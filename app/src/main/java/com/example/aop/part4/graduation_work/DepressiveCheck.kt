package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.aop.part4.graduation_work.data.RecordModel
import com.example.aop.part4.graduation_work.data.UserDiary
import com.example.aop.part4.graduation_work.databinding.DepressiveCheckBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.depressive_result.view.*
import kotlinx.android.synthetic.main.calandar.*
import java.lang.String.format
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DepressiveCheck : AppCompatActivity() {

    private lateinit var binding: DepressiveCheckBinding

    private var allcheck : Boolean = true
    private var id : String = ""        //아이디
    var value : Int = 0
    var database = Firebase.database.reference.child("depression_result")
    var Year : Int = 0
    var Month : Int = 0
    var Day : Int = 0
    var text : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DepressiveCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id") ?: ""

        /*var currenttime = Calendar.getInstance().time
        Year = Integer.parseInt(SimpleDateFormat("yyyy", Locale.getDefault()).format(currenttime))
        Month = Integer.parseInt(SimpleDateFormat("MM", Locale.getDefault()).format(currenttime))
        Day = Integer.parseInt(SimpleDateFormat("dd", Locale.getDefault()).format(currenttime))
        var Date = LocalDate.of(Year, Month, Day)
        var text = Date.format(DateTimeFormatter.ofPattern("yyyy. MM. dd"))*/

        var now = LocalDateTime.now()
        var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")
        var text = now.format(dateFormat)

        val sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE)

        if (id.isNullOrEmpty()) {
            id = sharedPreferences.getString("id", "") ?: ""
        } else {
            sharedPreferences.edit {
                this.putString("id", id)
                commit()
            }
        }

        with(binding) {
            fun value(){
                when(Q1.checkedRadioButtonId) {
                    Q1A1.id -> value += 0
                    Q1A2.id -> value += 1
                    Q1A3.id -> value += 2
                    Q1A4.id -> value += 3
                }
                allcheck = Q1.checkedRadioButtonId != -1

                when(Q2.checkedRadioButtonId) {
                    Q2A1.id -> value += 0
                    Q2A2.id -> value += 1
                    Q2A3.id -> value += 2
                    Q2A4.id -> value += 3
                }
                allcheck = Q2.checkedRadioButtonId != -1

                when(Q3.checkedRadioButtonId) {

                    Q3A1.id -> value += 0
                    Q3A2.id -> value += 1
                    Q3A3.id -> value += 2
                    Q3A4.id -> value += 3
                }
                allcheck = Q3.checkedRadioButtonId != -1

                when(Q4.checkedRadioButtonId) {

                    Q4A1.id -> value += 0
                    Q4A2.id -> value += 1
                    Q4A3.id -> value += 2
                    Q4A4.id -> value += 3
                }
                allcheck = Q4.checkedRadioButtonId != -1

                when(Q5.checkedRadioButtonId) {

                    Q5A1.id -> value += 3
                    Q5A2.id -> value += 2
                    Q5A3.id -> value += 1
                    Q5A4.id -> value += 0
                }
                allcheck = Q5.checkedRadioButtonId != -1

                when(Q6.checkedRadioButtonId) {

                    Q6A1.id -> value += 0
                    Q6A2.id -> value += 1
                    Q6A3.id -> value += 2
                    Q6A4.id -> value += 3
                }
                allcheck = Q6.checkedRadioButtonId != -1

                when(Q7.checkedRadioButtonId) {

                    Q7A1.id -> value += 0
                    Q7A2.id -> value += 1
                    Q7A3.id -> value += 2
                    Q7A4.id -> value += 3
                }
                allcheck = Q7.checkedRadioButtonId != -1

                when(Q8.checkedRadioButtonId) {

                    Q8A1.id -> value += 0
                    Q8A2.id -> value += 1
                    Q8A3.id -> value += 2
                    Q8A4.id -> value += 3
                }
                allcheck = Q8.checkedRadioButtonId != -1

                when(Q9.checkedRadioButtonId) {

                    Q9A1.id -> value += 0
                    Q9A2.id -> value += 1
                    Q9A3.id -> value += 2
                    Q9A4.id -> value += 3
                }
                allcheck = Q9.checkedRadioButtonId != -1

                when(Q10.checkedRadioButtonId) {

                    Q10A1.id -> value += 3
                    Q10A2.id -> value += 2
                    Q10A3.id -> value += 1
                    Q10A4.id -> value += 0
                }
                allcheck = Q10.checkedRadioButtonId != -1

                when(Q11.checkedRadioButtonId) {

                    Q11A1.id -> value += 0
                    Q11A2.id -> value += 1
                    Q11A3.id -> value += 2
                    Q11A4.id -> value += 3
                }
                allcheck = Q11.checkedRadioButtonId != -1

                when(Q12.checkedRadioButtonId) {

                    Q12A1.id -> value += 0
                    Q12A2.id -> value += 1
                    Q12A3.id -> value += 2
                    Q12A4.id -> value += 3
                }
                allcheck = Q12.checkedRadioButtonId != -1

                when(Q13.checkedRadioButtonId) {

                    Q13A1.id -> value += 0
                    Q13A2.id -> value += 1
                    Q13A3.id -> value += 2
                    Q13A4.id -> value += 3
                }
                allcheck = Q13.checkedRadioButtonId != -1

                when(Q14.checkedRadioButtonId) {

                    Q14A1.id -> value += 0
                    Q14A2.id -> value += 1
                    Q14A3.id -> value += 2
                    Q14A4.id -> value += 3
                }
                allcheck = Q14.checkedRadioButtonId != -1

                when(Q15.checkedRadioButtonId) {

                    Q15A1.id -> value += 3
                    Q15A2.id -> value += 2
                    Q15A3.id -> value += 1
                    Q15A4.id -> value += 0
                }
                allcheck = Q15.checkedRadioButtonId != -1

                when(Q16.checkedRadioButtonId) {

                    Q16A1.id -> value += 0
                    Q16A2.id -> value += 1
                    Q16A3.id -> value += 2
                    Q16A4.id -> value += 3
                }
                allcheck = Q16.checkedRadioButtonId != -1

                when(Q17.checkedRadioButtonId) {

                    Q17A1.id -> value += 0
                    Q17A2.id -> value += 1
                    Q17A3.id -> value += 2
                    Q17A4.id -> value += 3
                }
                allcheck = Q17.checkedRadioButtonId != -1

                when(Q18.checkedRadioButtonId) {

                    Q18A1.id -> value += 0
                    Q18A2.id -> value += 1
                    Q18A3.id -> value += 2
                    Q18A4.id -> value += 3
                }
                allcheck = Q18.checkedRadioButtonId != -1

                when(Q19.checkedRadioButtonId) {

                    Q19A1.id -> value += 0
                    Q19A2.id -> value += 1
                    Q19A3.id -> value += 2
                    Q19A4.id -> value += 3
                }
                allcheck = Q19.checkedRadioButtonId != -1

                when(Q20.checkedRadioButtonId) {

                    Q20A1.id -> value += 0
                    Q20A2.id -> value += 1
                    Q20A3.id -> value += 2
                    Q20A4.id -> value += 3
                }
                allcheck = Q20.checkedRadioButtonId != -1

                if (!allcheck){
                    //체크 되지 않은 항목 확인
                    Toast.makeText(applicationContext,"체크되지 않은 항목이 있습니다.",Toast.LENGTH_SHORT).show()
                    value = 0
                }

                else {

                    val model = RecordModel(id, value, text)
                    database.child(id!!).push().setValue(model)

                    /*val intent = Intent(this@DepressiveCheck, DepressionResult::class.java)
                    intent.putExtra("score", value)
                    Toast.makeText(applicationContext,value.toString(),Toast.LENGTH_SHORT).show()*/

                   val dialog = AlertDialog.Builder(this@DepressiveCheck)
                   val inflater = layoutInflater
                   val customView = inflater.inflate(R.layout.depressive_result, null)
                   dialog.setView(customView)
                    customView.result.text = value.toString()

                    if(value in 0..20) {
                        customView.depression_1.visibility = android.view.View.VISIBLE
                        customView.depression_text_1.visibility = android.view.View.VISIBLE
                    }
                    if(value in 21..24) {
                        customView.depression_2.visibility = android.view.View.VISIBLE
                        customView.depression_text_2.visibility = android.view.View.VISIBLE
                    }
                    if(value in 25..60) {
                        customView.depression_3.visibility = android.view.View.VISIBLE
                        customView.depression_text_3.visibility = android.view.View.VISIBLE
                    }

                   dialog.show()
                    value=0
                    //customView.backbtn.setOnClickListener {
                      //  Toast.makeText(applicationContext,value.toString(),Toast.LENGTH_SHORT).show()
                        //finish()
                    //}
                    customView.resultcheck.setOnClickListener {
                        val intent1 = Intent(this@DepressiveCheck, MainPage::class.java)
                        intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent1.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        finish()
                   }
                    //startActivity(intent)


                }
            }
            backbtn.setOnClickListener {
                finish()
            }

            completebtn.setOnClickListener {
                value()
            }
            check.setOnClickListener {
                value()
            }

        }
    }
}