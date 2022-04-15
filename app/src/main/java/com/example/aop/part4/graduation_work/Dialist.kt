package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.data.UserDialist
import com.example.aop.part4.graduation_work.data.UserDiary
import com.example.aop.part4.graduation_work.databinding.DialistBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class Dialist : AppCompatActivity() {

    private lateinit var binding : DialistBinding
    private val diarydatabase = Firebase.database.reference.child("diary")

    private val list = mutableListOf<UserDialist>()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            backbtn.setOnClickListener {
                val intent1 = Intent(this@Dialist, MainPage::class.java)
                startActivity(intent1)
                finish()
            }

            //메인페이지에서 id 받아오기
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

            NeWrite.setOnClickListener {
                //글쓰기
                val intent = Intent(this@Dialist, Diary::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                finish()
            }

        }

        binding.ItemList.addOnScrollListener(object  : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                //마지막 스크롤된 항목 확인
                val lastItem = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                if (lastItem == itemTotalCount) {
                    Log.d("SCROLL", "last Position...")
                }
            }
        })
    }

    private fun controlDatabase(id : String) {
        diarydatabase?.child(id).addChildEventListener(object:ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val getItem = snapshot.getValue(UserDiary::class.java)
                val getKey = snapshot.key

                val data = UserDialist(getKey!!, getItem!!.diary, getItem!!.day)

                list.add(data)
                var adapter = DiaryAdapter(list) { data -> adapterOnClick(data) }
                binding.ItemList.adapter = adapter
                binding.ItemList.addItemDecoration(DistanceItemDecorator(10))
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val updateItem = snapshot.getValue(UserDiary::class.java)
                val getKey = snapshot.key

                val data = UserDialist(getKey!!, updateItem!!.diary, updateItem!!.day)

                for (i in 0..list.size - 1) {
                    if (list[i].key == data!!.key) {
                        list.set(i, data)
                    }
                }
                var adapter = DiaryAdapter(list) { data -> adapterOnClick(data) }
                binding.ItemList.adapter = adapter
                binding.ItemList.addItemDecoration(DistanceItemDecorator(10))
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removeItem = snapshot.getValue(UserDiary::class.java)
                val getKey = snapshot.key

                val data = UserDialist(getKey!!, removeItem!!.diary, removeItem!!.day)

                list.remove(data)
                var adapter = DiaryAdapter(list) { data -> adapterOnClick(data) }
                binding.ItemList.adapter = adapter
                binding.ItemList.addItemDecoration(DistanceItemDecorator(10))
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun adapterOnClick(data : UserDialist) {
        //일기 보여줄 kt, xml 제작
        //해당 화면에서 삭제 및 수정 구현
        //타이틀 넣기
        //
        val intent = Intent(this@Dialist, Diary::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
        finish()
    }

    class DistanceItemDecorator(private val value: Int): RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            outRect.top = value
            outRect.bottom = value
            outRect.left = value
            outRect.right = value
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainPage::class.java)
        startActivity(intent)
        finish()
    }
}