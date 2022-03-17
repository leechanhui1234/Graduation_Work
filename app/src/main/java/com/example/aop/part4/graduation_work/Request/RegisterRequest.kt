package com.example.aop.part4.graduation_work.Request

import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.aop.part4.graduation_work.Url.Url.registerUrl

class RegisterRequest(
    userId: String,
    userPw: String,
    userName: String,
    userValue: String,
    userAge: Int,
    userEmail: String,
    listener: Response.Listener<String>?
): StringRequest(
    Method.POST, registerUrl, listener, null
) {
    private val map: MutableMap<String, String>

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String>? {
        return map
    }
    init {
        map = HashMap()
        map["userId"] = userId
        map["userPw"] = userPw
        map["userName"] = userName
        map["userAge"] = userAge.toString()
        map["userValue"] = userValue
        map["userEmail"] = userEmail
    }
}