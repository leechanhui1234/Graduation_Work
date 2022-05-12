package com.example.aop.part4.graduation_work.Hospital.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.data.Documents
import com.example.aop.part4.graduation_work.data.Rating
import com.example.aop.part4.graduation_work.data.totalReviewModel
import com.example.aop.part4.graduation_work.databinding.ChatAdapterBinding
import com.example.aop.part4.graduation_work.databinding.HospitalListAdapterBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class HospitalAdapter(private val onItemClicked: (Documents) -> Unit): ListAdapter<Documents, HospitalAdapter.ModelItemViewHolder> (
    diffUtil
){
    private val list = mutableListOf<totalReviewModel>()
    inner class ModelItemViewHolder(private val binding: HospitalListAdapterBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(model: Documents){
            binding.avgRatingBar.rating = 0.0f
            Firebase.database.reference.child("hospital").child(model.id).addChildEventListener(object: ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val data = snapshot.getValue(totalReviewModel:: class.java)
                    var ind = list.size - 1
                    if(ind != -1 && list[ind].info != data!!.info){
                        list.clear()
                    }
                    list.add(data!!)

                    var sum = 0.0f

                    for(i in 0..list.size - 1 step 1){
                        sum = sum + list[i].rating.toFloat()
                    }

                    if(list.size != 0){
                        binding.avgRatingBar.rating = ((Math.round(sum / list.size * 10) / 10.0).toFloat())
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

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
                return oldItem.address_name == newItem.address_name
            }

            override fun areContentsTheSame(oldItem: Documents, newItem: Documents): Boolean {
                return oldItem.address_name == newItem.address_name
            }
        }
    }
}