package com.example.aop.part4.graduation_work.More

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.aop.part4.graduation_work.More.Fragment.FragmentDeveloper
import com.example.aop.part4.graduation_work.More.Fragment.FragmentUser
import com.example.aop.part4.graduation_work.R
import com.example.aop.part4.graduation_work.data.UserData
import com.google.android.material.tabs.TabLayout

class MorePage: AppCompatActivity() {
    var fragment0: FragmentDeveloper ?= null
    var fragment1: FragmentUser ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.more_main)

        var data = intent.getParcelableExtra<UserData>("data")

        val sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("id", Context.MODE_PRIVATE)
        val sharedPreferences3 = getSharedPreferences("pw", Context.MODE_PRIVATE)
        val sharedPreferences4 = getSharedPreferences("email", Context.MODE_PRIVATE)

        var name = data?.userName
        var id = data?.userId
        var pw = data?.userPw
        var email = data?.userEmail

        if(name.isNullOrEmpty()) {
            name = sharedPreferences.getString("name", "") ?: ""
        } else {
            sharedPreferences.edit {
                this.putString("name", name)
                commit()
            }
        }

        if(id.isNullOrEmpty()) {
            id = sharedPreferences2.getString("id", "") ?: ""
        } else {
            sharedPreferences2.edit {
                this.putString("id", id)
                commit()
            }
        }

        if(pw.isNullOrEmpty()) {
            pw = sharedPreferences3.getString("pw", "") ?: ""
        } else {
            sharedPreferences3.edit {
                this.putString("pw", pw)
                commit()
            }
        }

        if(email.isNullOrEmpty()) {
            email = sharedPreferences4.getString("email", "") ?: ""
        } else {
            sharedPreferences4.edit {
                this.putString("email", email)
                commit()
            }
        }

        fragment0 = FragmentDeveloper()
        fragment1 = FragmentUser()

        supportFragmentManager.beginTransaction().add(R.id.frame, fragment0!!).commit()
        val tabs = findViewById<TabLayout>(R.id.tab_layout)
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_boy_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_person_24)
        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab!!.position

                var selected: Fragment ?= null

                when(position){
                    0 -> selected = fragment0
                    else -> selected = fragment1
                }

                var bundle = Bundle()
                bundle.putString("id", id)
                bundle.putString("name", name)
                bundle.putString("pw", pw)
                bundle.putString("email", email)
                selected!!.arguments = bundle

                supportFragmentManager.beginTransaction().replace(R.id.frame, selected!!).commit()

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}