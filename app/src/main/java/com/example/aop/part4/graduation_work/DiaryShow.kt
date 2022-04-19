package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import com.example.aop.part4.graduation_work.data.UserDialist
import com.example.aop.part4.graduation_work.databinding.DiaryShowBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DiaryShow : AppCompatActivity() {

    private val diarydatabase = Firebase.database.reference.child("diary")

    private val list = mutableListOf<UserDialist>()

    private lateinit var binding : DiaryShowBinding
    private lateinit var data : UserDialist
    private var id: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DiaryShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        with(binding) {

            id = intent.getStringExtra("id")!!
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
        Toast.makeText(this@DiaryShow, "${data.key}",Toast.LENGTH_SHORT).show()
        diarydatabase.child(id!!).child(data.key).removeValue()
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

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val diaryItem = snapshot.getValue(UserDialist::class.java)

                diaryItem ?: return

                removeChild(snapshot, diaryItem)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun removeChild(snapshot : DataSnapshot, diaryItem : UserDialist) {
        val data = snapshot.key?.let { key ->
            UserDialist(key, diaryItem.title, diaryItem.diary, diaryItem.day)
        }

        list.remove(data)
    }

}