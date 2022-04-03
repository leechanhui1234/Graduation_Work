package com.example.aop.part4.graduation_work

import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.data.UserDialist
import com.example.aop.part4.graduation_work.databinding.DialistListBinding
/*
class DiaryAdapter (private var onItemClicked : (UserDialist) -> Unit) : ListAdapter<UserDialist, DiaryAdapter.modelItemViewHolder> (diffUtil) {
    inner class modelItemViewHolder(private var binding : DialistListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: UserDialist, position : Int) {
            binding.content.text = model.Diary
            binding.time.text = model.Day

            binding.root.setOnClickListener {
                OnItemClicked(model)
            }
        }
    }

    override fun onBindViewHolder(holder: DiaryAdapter.modelItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryAdapter.modelItemViewHolder {
        TODO("Not yet implemented")
    }

}
*/