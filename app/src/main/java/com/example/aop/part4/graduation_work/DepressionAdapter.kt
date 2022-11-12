package com.example.aop.part4.graduation_work

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.data.RecordModel
import com.example.aop.part4.graduation_work.databinding.DepressiveRecordListBinding

class DepressionAdapter : ListAdapter<RecordModel, DepressionAdapter.ModelItemViewHolder>(diffUtil) {
    inner class ModelItemViewHolder(private val binding: DepressiveRecordListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: RecordModel, position: Int){
            binding.time.text = model.today
            binding.score.text = (model.score).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelItemViewHolder {
        return ModelItemViewHolder(DepressiveRecordListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: DepressionAdapter.ModelItemViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<RecordModel>(){
            override fun areItemsTheSame(oldItem: RecordModel, newItem: RecordModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecordModel, newItem: RecordModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}