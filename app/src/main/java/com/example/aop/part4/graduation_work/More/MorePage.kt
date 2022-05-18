package com.example.aop.part4.graduation_work.More

import androidx.fragment.app.Fragment
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.More.Fragment.FragmentDeveloper
import com.example.aop.part4.graduation_work.More.Fragment.FragmentUser
import com.example.aop.part4.graduation_work.R
import com.google.android.material.tabs.TabLayout

class MorePage: AppCompatActivity() {
    var fragment0: FragmentDeveloper ?= null
    var fragment1: FragmentUser ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.more_main)
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
                bundle.putString("test", "test")
                selected!!.arguments = bundle

                supportFragmentManager.beginTransaction().replace(R.id.frame, selected!!).commit()

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}