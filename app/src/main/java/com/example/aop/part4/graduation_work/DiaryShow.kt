package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import com.example.aop.part4.graduation_work.data.UserDialist
import com.example.aop.part4.graduation_work.data.UserDiary
import com.example.aop.part4.graduation_work.databinding.DiaryShowBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DiaryShow : AppCompatActivity() {

    private val diarydatabase = Firebase.database.reference.child("diary")

    private lateinit var binding : DiaryShowBinding
    private lateinit var data : UserDialist

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DiaryShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        with(binding) {

            var id = intent.getStringExtra("id")
            val sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE)
            if(id.isNullOrEmpty()){
                id = sharedPreferences.getString("id", "") ?: ""
            } else{
                sharedPreferences.edit {
                    this.putString("id", id)
                    commit()
                }
            }
            controlDatabase(id!!)

            backbtn.setOnClickListener {
                val intent1 = Intent(this@DiaryShow, Dialist::class.java)
                startActivity(intent1)
                finish()
            }

            menuclick.setOnClickListener {
                drawer.openDrawer(Gravity.RIGHT)
            }

            navigation.setNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.delete -> {
                        var dialog = AlertDialog.Builder(this@DiaryShow)
                            .setTitle("알람")
                            .setMessage("일기를 삭제하시겠습니까?")
                            .setPositiveButton("확인") {
                                dialog, which -> delete()
                            }.setNegativeButton("취소") {
                                dialog, which ->
                            }
                            .show()
                        return@setNavigationItemSelectedListener false
                    }
                    R.id.update -> {
                        update()
                        return@setNavigationItemSelectedListener false
                    }
                    else -> return@setNavigationItemSelectedListener true
                }
            }

            data = intent.getParcelableExtra("data")!!

            Day.text = data.day
            diaryTitle.text = data.title
            diaryText.text = data.diary
        }
    }

    private fun delete() {
        diarydatabase.child(data.key).removeValue()
        val intent_d = Intent(this@DiaryShow, Dialist::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent_d)
        finish()
    }

    private fun update() {
        var id = intent.getStringExtra("id")
        val intent_u = Intent(this@DiaryShow, DiaryUpdate::class.java)
        intent_u.putExtra("data", data)
        intent_u.putExtra("id", id)
        startActivity(intent_u)
    }

    private fun controlDatabase(id : String) {
        diarydatabase?.child(id).addChildEventListener(object: ChildEventListener {
            //DB 가져오기
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            }

            //수정
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            //삭제
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}