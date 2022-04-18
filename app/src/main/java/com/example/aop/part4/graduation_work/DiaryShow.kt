package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.aop.part4.graduation_work.data.UserDialist
import com.example.aop.part4.graduation_work.databinding.DialistBinding
import com.example.aop.part4.graduation_work.databinding.DiaryShowBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.nio.file.Files.delete

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
        diarydatabase.child(data!!.key).removeValue()
        val intent_d = Intent(this@DiaryShow, Dialist::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent_d)
        finish()
    }

    private fun update() {
        val intent_u = Intent(this@DiaryShow, DiaryUpdate::class.java)
        intent_u.putExtra("data", data)
        startActivity(intent_u)
    }

}