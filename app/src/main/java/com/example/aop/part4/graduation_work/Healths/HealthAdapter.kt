package com.example.aop.part4.graduation_work.Healths

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.R

class HealthAdapter(private val text: List<String>, private val url: List<String>): RecyclerView.Adapter<HealthAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthAdapter.ItemViewHolder {
        return HealthAdapter.ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.health_adapter, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HealthAdapter.ItemViewHolder, position: Int) {
        holder.bind(text[position], url[position])
    }

    override fun getItemCount(): Int {
        return 3
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val str: TextView = itemView.findViewById<TextView>(R.id.health_text)

        fun bind(str: String, url: String){
            this.str.text = str
        }
    }
}