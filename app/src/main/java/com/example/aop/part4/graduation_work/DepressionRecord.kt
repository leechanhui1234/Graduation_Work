package com.example.aop.part4.graduation_work

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop.part4.graduation_work.Board.ChatListActivity
import com.example.aop.part4.graduation_work.Board.WriteActivity
import com.example.aop.part4.graduation_work.Board.adapter.ChatAdapter
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.Board.model.ChatModel
import com.example.aop.part4.graduation_work.data.RecordModel
import com.example.aop.part4.graduation_work.databinding.ChatBinding
import com.example.aop.part4.graduation_work.databinding.DepressiveRecordBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DepressionRecord: AppCompatActivity() {
    private val list = mutableListOf<RecordModel>()

    private lateinit var binding: DepressiveRecordBinding
    var database = Firebase.database.reference.child("depression_result")
    private lateinit var adapter: DepressionAdapter
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DepressiveRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("id")

        var sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE)

        if (id.isNullOrEmpty()){
            id = sharedPreferences.getString("id", "") ?: ""
        } else {
            sharedPreferences.edit {
                this.putString("id", id)
                commit()
            }
        }

        list.clear()

        initAdapter()
        bindViews(id!!)
        controlDatabase()
    }

    private fun controlDatabase() {
        database.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val recordItem = snapshot.getValue(RecordModel::class.java)

                recordItem ?: return

                addChild(snapshot, recordItem)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun addChild(snapshot: DataSnapshot, recordItem: RecordModel) {
        val data = snapshot.key?.let { key ->
            RecordModel(recordItem.userId, recordItem.score, recordItem.today)
        }

        var list2 = mutableListOf(data)
        val totallist = list2 + list

        list.clear()
        totallist.map{
            list.add(it!!)
        }

        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    private fun initAdapter(){
        adapter = DepressionAdapter()
    }

    private fun bindViews(id: String) {
        database = Firebase.database.reference.child("depression_result").child(id)
        with(binding) {
            ItemList.adapter = adapter
            ItemList.layoutManager = LinearLayoutManager(this@DepressionRecord)
            backbtn.setOnClickListener {
                finish()
            }
            userId.text = id
        }
    }
}