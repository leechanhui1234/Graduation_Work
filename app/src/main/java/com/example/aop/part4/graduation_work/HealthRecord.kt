package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.aop.part4.graduation_work.data.UserHealthCheck
import com.example.aop.part4.graduation_work.databinding.HealthRecordBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HealthRecord : AppCompatActivity() {

    private lateinit var binding: HealthRecordBinding

    private var id : String = ""    //아이디

    var database = Firebase.database.reference.child("health_select")

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id") ?: ""

        val sharedPreferences3 = getSharedPreferences("id", Context.MODE_PRIVATE)

        if (id.isNullOrEmpty()) {
            id = sharedPreferences3.getString("id", "") ?: ""
        } else {
            sharedPreferences3.edit {
                this.putString("id", id)
                commit()
            }
        }

        with(binding) {

        }
    }

    private fun controlDatabase(id : String) {
        database?.child(id!!).addChildEventListener(object: ChildEventListener {
            //DB 가져오기
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val getItem = snapshot.getValue(UserHealthCheck::class.java)
                val getKey = snapshot.key

                //val data = UserHealthCheck(getKey!!, getItem!!.date, getItem!!.pre_select, getItem!!.in_select, getItem!!.post_select, getItem!!.record)
            }

            //수정
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            //삭제
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}