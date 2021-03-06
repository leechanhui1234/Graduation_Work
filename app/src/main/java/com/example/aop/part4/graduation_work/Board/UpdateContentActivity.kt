package com.example.aop.part4.graduation_work.Board

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.databinding.ChatUpdateBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UpdateContentActivity : AppCompatActivity(){
    private lateinit var binding: ChatUpdateBinding

    private val database = Firebase.database.reference.child("board")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ChatUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = intent.getParcelableExtra<ChatKeyModel>("model")

        val hashmap = HashMap<String, ChatKeyModel>()

        with(binding) {
            backbtn.setOnClickListener {
                finish()
            }

            completebtn.setOnClickListener {
                val data = ChatKeyModel(model!!.userId, title.text.toString(), content.text.toString(), model!!.time, model!!.key)
                hashmap.put("${model!!.key}", data)
                database.updateChildren(hashmap as Map<String, Any>)
                val intent = Intent(this@UpdateContentActivity, ChatActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
    }
}