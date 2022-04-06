package com.example.aop.part4.graduation_work.Board

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop.part4.graduation_work.Board.adapter.ChatAdapter
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.Board.model.ChatModel
import com.example.aop.part4.graduation_work.databinding.ChatBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private val list = mutableListOf<ChatKeyModel>()

    private lateinit var binding: ChatBinding
    private val database = Firebase.database.reference.child("board")
    private lateinit var adapter: ChatAdapter
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.getStringExtra("name")
        id = intent.getStringExtra("id")

        val sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
        var sharedPreferences2 = getSharedPreferences("id", Context.MODE_PRIVATE)

        if(name.isNullOrEmpty()){
            name = sharedPreferences.getString("name", "") ?: ""
        } else {
            sharedPreferences.edit {
                this.putString("name", name)
                commit()
            }
        }

        if(id.isNullOrEmpty()){
            id = sharedPreferences2.getString("id", "") ?: ""
        } else {
            sharedPreferences2.edit {
                this.putString("id", id)
                commit()
            }
        }

        list.clear()

        initAdapter()
        bindViews(name)
        controlDatabase()
    }

    private fun controlDatabase() {
        database?.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatModel::class.java)

                chatItem ?: return

                addChild(snapshot, chatItem)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val data = snapshot.getValue(ChatKeyModel::class.java)

                data ?: return

                updateChild(data)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val chatItem = snapshot.getValue(ChatModel::class.java)

                chatItem ?: return

                removeChild(snapshot, chatItem)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun addChild(snapshot: DataSnapshot, chatItem: ChatModel) {
        val data = snapshot.key?.let { key ->
            ChatKeyModel(chatItem.userId, chatItem.title, chatItem.content, chatItem.time, key)
        }

        var list2 = mutableListOf(data)
        val totallist = list2 + list

        list.clear()
        totallist.map{
            list.add(it!!)
        }

        adapter.submitList(list)
        adapter.notifyDataSetChanged()

        binding.progress.visibility = View.GONE
        binding.progresstext.visibility = View.GONE
    }

    private fun updateChild(data: ChatKeyModel){
        for(i in 0..list.size - 1){
            if(list[i].key == data!!.key){
                list.set(i, data)
            }
        }
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    private fun removeChild(snapshot: DataSnapshot, chatItem: ChatModel){
        val data = snapshot.key?.let { key ->
            ChatKeyModel(chatItem.userId, chatItem.title, chatItem.content, chatItem.time, key)
        }

        list.remove(data)
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    private fun bindViews(name: String) {
        with(binding){
            chatlist.adapter = adapter
            chatlist.layoutManager = LinearLayoutManager(this@ChatActivity)

            writebtn.setOnClickListener {
                val intent = Intent(this@ChatActivity, WriteActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }

            backbtn.setOnClickListener {
                finish()
            }
        }
    }

    private fun initAdapter() {
        adapter = ChatAdapter(onItemClicked = { model ->
            val intent = Intent(this@ChatActivity, ChatListActivity::class.java)
            intent.putExtra("model", model)
            intent.putExtra("id", id)
            startActivity(intent)
        })
    }
}