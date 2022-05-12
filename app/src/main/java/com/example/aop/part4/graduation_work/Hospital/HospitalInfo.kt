package com.example.aop.part4.graduation_work.Hospital

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
import java.lang.Math.round
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HospitalInfo: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: HospitalInfoBinding
    private lateinit var mgoogleMap: GoogleMap
    private lateinit var d: Documents
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
        d = intent.getParcelableExtra<Documents>("data")!!
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
            addr.setText(d.address_name)
            roadAddr.setText(d.road_address_name)
            location.setText("(${d.y}, ${d.x})")
            phone.setText(d.phone)
            title.setText(d.place_name)
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
                val saveData = totalReviewModel(id = id!!, rating = text, review = review.text.toString(), date = Strnow, info = d.id)
                database.child(d.id).push().setValue(saveData)
                AlertDialog.Builder(this@HospitalInfo)
                    .setTitle("후기 등록 완료")
                    .setMessage("후기 등록이 완료되었습니다.")
                    .setPositiveButton("확인"){ _, _ ->
                    }.show()
                binding.ratingBar.rating = 0.0f
                binding.score.setText("0")
                binding.review.setText("")
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mgoogleMap = googleMap
        val location = LocationLatLngEntity(d.x.toFloat(), d.y.toFloat())
        val markerOption = MarkerOptions().apply{
            position(LatLng(location.longitude.toDouble(), location.latitude.toDouble()))
            title(d.place_name)
            snippet(d.address_name)
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
        database?.child(d!!.id).addChildEventListener(object: ChildEventListener {
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
            totalReviewModel(id = ReviewItem.id, rating = ReviewItem.rating, review = ReviewItem.review, date = ReviewItem.date, info = d.id)
        }

        list.add(data!!)
        adapter.setReviewList(list){

        }

        var sum = 0.0f

        for(i in 0..list.size - 1 step 1){
            sum = sum + list[i].rating.toFloat()
        }

        if(list.size != 0){
            binding.avgScore.setText((round(sum/list.size * 10) / 10.0).toString())
            binding.avgRatingBar.rating = binding.avgScore.text.toString().toFloat()
        }
        adapter.notifyDataSetChanged()
    }

    companion object{
        const val CAMERA_ZOOM_LEVEL = 17f
    }
}