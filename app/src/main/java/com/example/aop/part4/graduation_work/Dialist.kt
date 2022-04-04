package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.data.UserDialist
import com.example.aop.part4.graduation_work.databinding.DialistBinding

class Dialist : AppCompatActivity() {

    private lateinit var binding : DialistBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            Delete.setOnClickListener {
                //삭제
            }

            NeWrite.setOnClickListener {
                //글쓰기
                val intent = Intent(this@Dialist, Diary::class.java)
                startActivity(intent)
                finish()
            }

            reWrite.setOnClickListener {
                //수정하기
            }
        }

        val list = ArrayList<UserDialist> ()
        list.apply {
            add(UserDialist("test1", "1"))
            add(UserDialist("test2", "2"))
            add(UserDialist("test3", "3"))
        }

        var adapter = DiaryAdapter(list, {data -> adapterOnClick(data)})
        binding.ItemList.adapter = adapter
        binding.ItemList.addItemDecoration(DistanceItemDecorator(10))

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

    private fun adapterOnClick(data : UserDialist) {
        Toast.makeText(applicationContext, "clicked -> ${data.Diary}, ${data.Day}", Toast.LENGTH_SHORT).show()
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