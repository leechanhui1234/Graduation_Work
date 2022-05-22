package com.example.aop.part4.graduation_work.More.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.aop.part4.graduation_work.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentDeveloper : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_developer, container, false)
        val query = view.findViewById<Button>(R.id.query)
        query.setOnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.setType("plain/text")
            val address = arrayOf("1724535@donga.ac.kr")
            email.putExtra(Intent.EXTRA_EMAIL, address)
            startActivity(email)
        }
        return view
    }
}