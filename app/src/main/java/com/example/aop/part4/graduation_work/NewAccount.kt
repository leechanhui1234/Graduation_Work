package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.jar.Attributes

class NewAccount : AppCompatActivity() {

    private lateinit var Name : EditText
    private lateinit var ID : EditText
    private lateinit var PW : EditText
    private lateinit var PW_CH : EditText
    private lateinit var Sex_Group : RadioGroup
    private lateinit var Male : RadioButton
    private lateinit var Female : RadioButton
    private lateinit var Email : EditText

    private lateinit var Save : Button
    private lateinit var Login : Button

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_account)

        var Age = findViewById<Spinner>(R.id.Age)
        var age_list = resources.getStringArray(R.array.age)
        var adapterAge: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, age_list)
        Age.adapter = adapterAge

        Save = findViewById<Button>(R.id.Save)
        Save.setOnClickListener {
            Name = findViewById(R.id.Name)
            ID = findViewById(R.id.ID)
            PW = findViewById(R.id.PW)
            PW_CH = findViewById(R.id.PW_CH)
            Sex_Group = findViewById(R.id.Sex_Group)
            Male = findViewById(R.id.Male)
            Female = findViewById(R.id.Female)
            Email = findViewById(R.id.Email)

            //이름 공백 확인
            if (Name == null) {
                Toast.makeText(applicationContext, "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
            }

            //아이디 길이 확인
            if (ID.text.toString().length < 8) {
                Toast.makeText(applicationContext, "8자 이상의 아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            
            //비밀번호에 대문자 및 특수문자 포함 여부 확인
            var regex1 = Regex("""[A-Z]""") //비밀번호 대문자
            var regex2 = Regex("""[~|!|@|#|$|%|^|&|*|(|)|_|+|/|.|`|<|>|?]""") //비밀번호 특수문자
            if (PW == null) {
                Toast.makeText(applicationContext, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if (!regex1.containsMatchIn(PW.text.toString())) {
                Toast.makeText(applicationContext, "비밀번호에 대문자가 포함되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
            else if (!regex2.containsMatchIn(PW.text.toString())) {
                Toast.makeText(applicationContext, "비밀번호에 특수문자가 포함되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
            else if (PW.text.toString().length < 8) {
                Toast.makeText(applicationContext, "8자 이상의 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }

            //비밀번호 일치 여부 판별
            if (!PW.text.toString().equals(PW_CH.text.toString())) {
                Toast.makeText(applicationContext, "비밀번호를 다시 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        Login = findViewById<Button>(R.id.Login)
        Login.setOnClickListener {
            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}