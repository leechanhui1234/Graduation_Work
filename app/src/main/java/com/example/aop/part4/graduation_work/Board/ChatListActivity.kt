package com.example.aop.part4.graduation_work.Board

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop.part4.graduation_work.Board.adapter.ChatListAdapter
import com.example.aop.part4.graduation_work.Board.model.ChatAdapterListModel
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.Board.model.ChatListModel
import com.example.aop.part4.graduation_work.R
import com.example.aop.part4.graduation_work.databinding.ChatListBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ChatListActivity: AppCompatActivity() {
    private lateinit var binding: ChatListBinding

    private val database = Firebase.database.reference.child("board")
    private val chatdatabase = Firebase.database.reference.child("chat")

    private val list = mutableListOf<ChatAdapterListModel>()
    private lateinit var model: ChatKeyModel
    private lateinit var adapter: ChatListAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        model = intent.getParcelableExtra<ChatKeyModel>("model")!!
        var userId = intent.getStringExtra("id")!!

        var id = model.userId

        if(id.equals(userId)){
            binding.menuclick.visibility = View.VISIBLE
        } else {
            binding.menuclick.visibility = View.GONE
        }

        adapter = ChatListAdapter(onItemClicked = {
            chatdatabase.child(model!!.key).child(it.key).removeValue()
        })

        controlSharedPreferences()
        bindViews()
        controlDatabase()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindViews() {
        with(binding){
            chatlist.adapter = adapter
            chatlist.layoutManager = LinearLayoutManager(this@ChatListActivity)

            title.text = model?.title.toString()
            content.text = model?.content.toString()

            list.clear()

            sendbtn.setOnClickListener {
                var text = message.text.toString()
                if (text.isEmpty()) {
                    Toast.makeText(applicationContext, "공백은 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    val now = LocalDate.now()
                    var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    var userId = model?.userId
                    val chatList = ChatListModel(userId!!, text, Strnow)
                    chatdatabase.child(model!!.key).push().setValue(chatList)

                    message.setText("")
                }
            }

            backbtn.setOnClickListener {
                finish()
            }

            menuclick.setOnClickListener {
                drawer.openDrawer(Gravity.RIGHT)
            }

            navigation.setNavigationItemSelectedListener{
                when(it.itemId){
                    R.id.delete -> {
                        delete()
                        return@setNavigationItemSelectedListener false
                    }

                    R.id.update -> {
                        update()
                        return@setNavigationItemSelectedListener false
                    }

                    else -> return@setNavigationItemSelectedListener true
                }
            }
        }
    }

    private fun delete(){
        chatdatabase.child(model!!.key).removeValue()
        database.child(model!!.key).removeValue()
        val intent = Intent(this@ChatListActivity, ChatActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun update(){
        val intent = Intent(this@ChatListActivity, UpdateContentActivity::class.java)
        intent.putExtra("model", model)
        startActivity(intent)
    }

    private fun controlSharedPreferences() {
        val sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)

        if(model?.title.isNullOrEmpty()){
            val userId = sharedPreferences.getString("userId", "") ?: ""
            val title = sharedPreferences.getString("title", "") ?: ""
            val content = sharedPreferences.getString("content", "") ?: ""
            val time = sharedPreferences.getString("time", "") ?: ""
            val key = sharedPreferences.getString("key", "") ?: ""

            model = ChatKeyModel(userId, title, content, time, key)
        } else {
            sharedPreferences.edit {
                this.putString("userId", model?.userId)
                this.putString("title", model?.title)
                this.putString("content", model?.content)
                this.putString("time", model?.time)
                this.putString("key", model?.key)
                commit()
            }
        }
    }

    private fun controlDatabase() {
        chatdatabase?.child(model!!.key).addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(ChatListModel::class.java)

                chatItem ?: return

                addChild(snapshot, chatItem)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val chatItem = snapshot.getValue(ChatListModel::class.java)

                chatItem ?: return

                removeChild(snapshot, chatItem)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun addChild(snapshot: DataSnapshot, chatItem: ChatListModel) {
        val data = snapshot.key?.let { key ->
            ChatAdapterListModel(chatItem.userId, chatItem.text, chatItem.time, key)
        }

        list.add(data!!)
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    private fun removeChild(snapshot: DataSnapshot, chatItem: ChatListModel) {
        val data = snapshot.key?.let { key ->
            ChatAdapterListModel(chatItem.userId, chatItem.text, chatItem.time, key)
        }

        list.remove(data)
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }
}