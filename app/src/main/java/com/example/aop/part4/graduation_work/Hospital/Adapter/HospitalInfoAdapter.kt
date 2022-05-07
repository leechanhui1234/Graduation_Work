package com.example.aop.part4.graduation_work.Hospital.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.data.Documents
import com.example.aop.part4.graduation_work.data.totalReviewModel
import com.example.aop.part4.graduation_work.databinding.HospitalInfoAdapterBinding

class HospitalInfoAdapter: RecyclerView.Adapter<HospitalInfoAdapter.ModelItemViewHolder>(){
    private var ReviewList = mutableListOf<totalReviewModel>()
    private lateinit var ReviewClickListener: (totalReviewModel) -> Unit

    inner class ModelItemViewHolder(val binding: HospitalInfoAdapterBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(data: totalReviewModel) = with(binding){ //binding 내부의 변수를 사용
            rating.rating = data.rating.toFloat()
            user.setText(data.id)
            date.setText(data.date)
            review.setText(data.review)
            score.setText(data.rating)
        }

        fun bindViews(data:totalReviewModel) {
            binding.root.setOnClickListener {
                ReviewClickListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelItemViewHolder {
        val view = HospitalInfoAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModelItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelItemViewHolder, position: Int) {
        Log.e("Test", holder.toString())
        holder.bindData(ReviewList[position])
        holder.bindViews(ReviewList[position])
    }

    override fun getItemCount(): Int {
        Log.e("Test", ReviewList.size.toString())
        return ReviewList.size
    }

    fun setReviewList(ReviewList: MutableList<totalReviewModel>, ReviewClickListener:(totalReviewModel) -> Unit){
        this.ReviewList= ReviewList
        this.ReviewClickListener = ReviewClickListener
        notifyDataSetChanged()
    }
}