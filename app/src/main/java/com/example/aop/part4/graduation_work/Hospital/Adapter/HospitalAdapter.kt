package com.example.aop.part4.graduation_work.Hospital.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.data.Documents
import com.example.aop.part4.graduation_work.databinding.ChatAdapterBinding
import com.example.aop.part4.graduation_work.databinding.HospitalListAdapterBinding

class HospitalAdapter(private val onItemClicked: (Documents) -> Unit): ListAdapter<Documents, HospitalAdapter.ModelItemViewHolder> (
    diffUtil
){
    inner class ModelItemViewHolder(private val binding: HospitalListAdapterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: Documents){
            binding.title.text = model.place_name
            binding.phone.text = model.phone
            binding.address.text = model.address_name

            binding.root.setOnClickListener {
                onItemClicked(model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelItemViewHolder {
        return ModelItemViewHolder(HospitalListAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ModelItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<Documents>(){
            override fun areItemsTheSame(oldItem: Documents, newItem: Documents): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Documents, newItem: Documents): Boolean {
                return oldItem == newItem
            }
        }
    }
}