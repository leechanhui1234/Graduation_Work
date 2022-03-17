package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.aop.part4.graduation_work.Request.RegisterRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject
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
    private lateinit var Age: Spinner

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
            Age = findViewById(R.id.Age)
            
            //비밀번호에 대문자 및 특수문자 포함 여부 확인
            var regex1 = Regex("""[A-Z]""") //비밀번호 대문자
            var regex2 = Regex("""[~|!|@|#|$|%|^|&|*|(|)|_|+|/|.|`|<|>|?]""") //비밀번호 특수문자

            if (Name == null) { //이름 공백 확인
                Toast.makeText(applicationContext, "이름을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if (ID.text.toString().length < 8) { //아이디 길이 확인
                Toast.makeText(applicationContext, "8자 이상의 아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if (PW == null) {
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
            else if (!PW.text.toString().equals(PW_CH.text.toString())) { //비밀번호 일치 여부 판별
                Toast.makeText(applicationContext, "비밀번호를 다시 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                var name = Name.text.toString()
                var id = ID.text.toString()
                var pw = PW.text.toString()
                var sex = when(Sex_Group.checkedRadioButtonId){
                    R.id.Male -> "남성"
                    else -> "여성"
                }
                var email = Email.text.toString()
                var age = Age.selectedItem.toString()
                if(age === "나이를 선택하세요") Toast.makeText(this, "나이를 선택해주세요", Toast.LENGTH_SHORT).show()
                else {
                    //Toast.makeText(this, age, Toast.LENGTH_SHORT).show()
                    InputDatabase(id, pw, name, sex, age.toInt(), email)
                }
            }
        }

        Login = findViewById<Button>(R.id.Login)
        Login.setOnClickListener {
            var intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun InputDatabase(userId: String, userPw: String, userName: String, userValue: String, userAge: Int, userEmail: String) {
        val responseListener = Response.Listener<String> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                if(success){
                    Toast.makeText(applicationContext, "회원 등록에 성공하셨습니다. 로그인하기 버튼을 클릭해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "중복된 아이디입니다. 아이디를 다시 입력해주세요", Toast.LENGTH_SHORT).show()
                    ID.setText("")
                    return@Listener
                }
            } catch(e: Exception){
                e.printStackTrace()
                Log.d("NewAccount", e.message.toString())
                Toast.makeText(this, "예상치 못한 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val registerRequest =
            RegisterRequest(userId, userPw, userName, userValue, userAge, userEmail, responseListener)
        val queue = Volley.newRequestQueue(this@NewAccount)
        queue.add(registerRequest)
    }
}