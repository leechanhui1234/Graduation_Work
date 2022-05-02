package com.example.aop.part4.graduation_work.Hospital

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop.part4.graduation_work.Board.model.ChatAdapterListModel
import com.example.aop.part4.graduation_work.Board.model.ChatListModel
import com.example.aop.part4.graduation_work.Hospital.Adapter.HospitalInfoAdapter
import com.example.aop.part4.graduation_work.R
import com.example.aop.part4.graduation_work.data.Documents
import com.example.aop.part4.graduation_work.data.LocationLatLngEntity
import com.example.aop.part4.graduation_work.data.totalReviewModel
import com.example.aop.part4.graduation_work.databinding.HospitalInfoBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.hospital_list_adapter.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HospitalInfo: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: HospitalInfoBinding
    private lateinit var mgoogleMap: GoogleMap
    private lateinit var data: Documents
    private lateinit var adapter: HospitalInfoAdapter
    private var id: String ?= null
    private val database = Firebase.database.reference.child("hospital")
    private val list = mutableListOf<totalReviewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HospitalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backbtn.setOnClickListener {
            finish()
        }
        data = intent.getParcelableExtra<Documents>("data")!!
        id = intent.getStringExtra("id")
        adapter = HospitalInfoAdapter()
        initAdapter()
        bindView()
        controlDatabase()
        val supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindView(){
        with(binding){
            addr.setText(data.address_name)
            roadAddr.setText(data.road_address_name)
            location.setText("(${data.y}, ${data.x})")
            phone.setText(data.phone)
            title.setText(data.place_name)
            ratingBar.setOnRatingBarChangeListener { ratingBar1, rating, fromUser ->
                ratingBar.rating = rating
                score.setText(rating.toInt().toString())
            }
            register.setOnClickListener {
                registerFirebase()
            }
        }
    }

    private fun initAdapter() {
        with(binding){
            totalReviewAdapter.adapter = adapter
            totalReviewAdapter.layoutManager = LinearLayoutManager(this@HospitalInfo)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerFirebase() {
        with(binding){
            var text = score.text.toString()
            if(text.toInt() == 0) Toast.makeText(this@HospitalInfo, "평점을 선택해주세요", Toast.LENGTH_SHORT).show()
            else if(review.text.isNullOrEmpty()) Toast.makeText(this@HospitalInfo, "후기를 입력해주세요", Toast.LENGTH_SHORT).show()
            else{
                val now = LocalDate.now()
                var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                val saveData = totalReviewModel(id = id!!, rating = text, review = review.text.toString(), date = Strnow)
                database.child(data.id).push().setValue(saveData)
                AlertDialog.Builder(this@HospitalInfo)
                    .setTitle("후기 등록 완료")
                    .setMessage("후기 등록이 완료되었습니다.")
                    .setPositiveButton("확인"){ _, _ ->
                    }.show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mgoogleMap = googleMap
        val location = LocationLatLngEntity(data.x.toFloat(), data.y.toFloat())
        val markerOption = MarkerOptions().apply{
            position(LatLng(location.longitude.toDouble(), location.latitude.toDouble()))
            title(data.place_name)
            snippet(data.address_name)
        }

        googleMap.addMarker(markerOption)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location.longitude.toDouble(),
                    location.latitude.toDouble()
                ), CAMERA_ZOOM_LEVEL))
        googleMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun controlDatabase() {
        database?.child(data!!.id).addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val item = snapshot.getValue(totalReviewModel::class.java)
                item ?: return
                addChild(snapshot, item)
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
    }

    private fun addChild(snapshot: DataSnapshot, ReviewItem: totalReviewModel) {
        val data = snapshot.key?.let { key ->
            totalReviewModel(id = ReviewItem.id, rating = ReviewItem.rating, review = ReviewItem.review, date = ReviewItem.date)
        }

        list.add(data!!)
        adapter.setReviewList(list){
            Log.e("Test", it.toString())
        }
        adapter.notifyDataSetChanged()
    }

    /*private fun removeChild(snapshot: DataSnapshot, chatItem: ChatListModel) {
        val data = snapshot.key?.let { key ->
            ChatAdapterListModel(chatItem.userId, chatItem.text, chatItem.time, key)
        }

        list.remove(data)
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }*/

    companion object{
        const val CAMERA_ZOOM_LEVEL = 17f
    }
}