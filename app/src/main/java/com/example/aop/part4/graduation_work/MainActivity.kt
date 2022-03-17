package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var ID : EditText
    private lateinit var PW : EditText
    lateinit var Login : Button

    lateinit var AutoLogin : CheckBox
    private lateinit var sharePreferences: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    lateinit var New_Account : Button
    lateinit var Find_ID : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ID = findViewById(R.id.ID)
        PW = findViewById(R.id.PW)

        Login = findViewById<Button>(R.id.Login)
        Login.setOnClickListener {
            if (AutoLogin.isChecked) {
                editor.putBoolean(getString(R.string.auto_login), true)
                editor.apply()
                editor.putString(getString(R.string.prompt_ID), ID.text.toString())
                editor.putString(getString(R.string.prompt_PW), PW.text.toString())
                editor.commit()
            }
            else {
                editor.putBoolean(getString(R.string.auto_login), false)
                editor.putString(getString(R.string.prompt_ID), ID.text.toString())
                editor.putString(getString(R.string.prompt_PW), PW.text.toString())
                editor.commit()
            }
        }

        New_Account = findViewById(R.id.NewAccount)
        New_Account.setOnClickListener {
            val intent = Intent(this, NewAccount::class.java)
            startActivity(intent)
        }

        sharePreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        editor = sharePreferences.edit()

        checkSharedPreference()
    }

    @SuppressLint("StringFormatInvalid")
    private fun checkSharedPreference() {
        AutoLogin = findViewById(R.id.AutoLogin)
        AutoLogin.isChecked = sharePreferences.getBoolean(getString(R.string.auto_login), false)
        ID.setText(sharePreferences.getString(getString(R.string.prompt_ID), ""))
        PW.setText(sharePreferences.getString(getString(R.string.prompt_PW), ""))
    }
}
