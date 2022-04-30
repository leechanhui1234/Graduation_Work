package com.example.aop.part4.graduation_work.Hospital

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop.part4.graduation_work.Board.adapter.ChatAdapter
import com.example.aop.part4.graduation_work.Board.adapter.ChatListAdapter
import com.example.aop.part4.graduation_work.Hospital.Adapter.HospitalAdapter
import com.example.aop.part4.graduation_work.databinding.HospitalListBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HospitalList: AppCompatActivity() {
    private val scope = MainScope()
    private lateinit var binding: HospitalListBinding
    private lateinit var adapter: HospitalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HospitalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HospitalAdapter(onItemClicked = { it ->

        })

        initAdapter()

        searchKeyword()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun initAdapter() {
        with(binding){
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(this@HospitalList)
        }
    }

    private fun searchKeyword(){
        scope.launch {
            val data = Repository.getListApi("129.034123" ,"35.142898", "정신병원")
            adapter.submitList(data)
            adapter.notifyDataSetChanged()
        }
    }
}