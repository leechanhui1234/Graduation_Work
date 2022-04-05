package com.example.aop.part4.graduation_work.Board.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.Board.model.ChatAdapterListModel
import com.example.aop.part4.graduation_work.databinding.ChatListAdapterBinding

class ChatListAdapter(private val onItemClicked: (ChatAdapterListModel) -> Unit): ListAdapter<ChatAdapterListModel, ChatListAdapter.ItemViewHolder>(
    diffUtil
){
    inner class ItemViewHolder(private val binding: ChatListAdapterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: ChatAdapterListModel, position: Int){
            binding.content.text = model.text
            binding.time.text = model.time

            binding.deletebtn.setOnClickListener {
                onItemClicked(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ChatListAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<ChatAdapterListModel>(){
            override fun areItemsTheSame(oldItem: ChatAdapterListModel, newItem: ChatAdapterListModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ChatAdapterListModel,
                newItem: ChatAdapterListModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}