package com.example.aop.part4.graduation_work.More.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aop.part4.graduation_work.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentUser : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        val test = arguments?.getString("test") ?: "null"
        Log.e("Main", test!!.toString())
        return view
    }
}