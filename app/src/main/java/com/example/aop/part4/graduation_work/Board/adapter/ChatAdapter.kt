package com.example.aop.part4.graduation_work.Board.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.databinding.ChatAdapterBinding

class ChatAdapter(private val onItemClicked: (ChatKeyModel) -> Unit): ListAdapter<ChatKeyModel, ChatAdapter.ModelItemViewHolder> (
    diffUtil
){
    inner class ModelItemViewHolder(private val binding: ChatAdapterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: ChatKeyModel, position: Int){
            binding.title.text = model.title
            binding.content.text = model.content
            binding.time.text = model.time

            binding.root.setOnClickListener {
                onItemClicked(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelItemViewHolder {
        return ModelItemViewHolder(ChatAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ModelItemViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<ChatKeyModel>(){
            override fun areItemsTheSame(oldItem: ChatKeyModel, newItem: ChatKeyModel): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(oldItem: ChatKeyModel, newItem: ChatKeyModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}