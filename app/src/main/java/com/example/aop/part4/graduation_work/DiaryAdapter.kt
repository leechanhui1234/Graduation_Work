package com.example.aop.part4.graduation_work

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.data.UserDialist

class DiaryAdapter(private val items: ArrayList<UserDialist>, private val onClick: (UserDialist) -> Unit) : RecyclerView.Adapter<DiaryAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        var listner = View.OnClickListener { it ->
            item.let {
                onClick(item)
            }
        }

        holder.apply {
            bind(listner, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflated = LayoutInflater.from(parent.context).inflate(R.layout.dialist_list, parent, false)
        return ViewHolder(inflated)
    }

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v) {
        private var view : View = v
        fun bind(listner: View.OnClickListener, item:UserDialist) {
            var content = view.findViewById<TextView>(R.id.content)
            var time = view.findViewById<TextView>(R.id.time)
            content.text = item.Diary
            time.text = item.Day

            view.setOnClickListener(listner)
        }
    }
}