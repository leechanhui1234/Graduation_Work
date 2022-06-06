package com.example.aop.part4.graduation_work

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.databinding.DepressiveCheckBinding

class DepressiveCheck : AppCompatActivity() {

    private lateinit var binding: DepressiveCheckBinding

    private var allcheck : Boolean = true
    var value : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DepressiveCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    val intent = Intent(this@DepressiveCheck, DepressionResult::class.java)
                    intent.putExtra("score", value)
                    startActivity(intent)
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