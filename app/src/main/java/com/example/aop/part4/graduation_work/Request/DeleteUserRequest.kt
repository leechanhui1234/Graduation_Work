package com.example.aop.part4.graduation_work.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.aop.part4.graduation_work.Url.Url

class DeleteUserRequest(
    userId: String,
    userPw: String,
    listener : Response.Listener<String>?
): StringRequest(
    Method.POST, Url.deleteUrl, listener, null
) {
    private val map : MutableMap<String, String>

    @Throws(AuthFailureError::class)
    override fun getParams() : Map<String, String>? {
        return map
    }
    init {
        map = HashMap()
        map["userId"] = userId
        map["userPw"] = userPw
    }
}