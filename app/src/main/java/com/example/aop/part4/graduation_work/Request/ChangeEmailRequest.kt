package com.example.aop.part4.graduation_work.Request

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.aop.part4.graduation_work.Url.Url

class ChangeEmailRequest(
    userId: String,
    userEmail: String,
    listener : Response.Listener<String>?
): StringRequest(
    Method.POST, Url.emailchangeUrl, listener, null
) {
    private val map : MutableMap<String, String>

    @Throws(AuthFailureError::class)
    override fun getParams() : Map<String, String>? {
        return map
    }
    init {
        map = HashMap()
        map["userId"] = userId
        map["userEmail"] = userEmail
    }
}