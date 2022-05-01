package com.example.aop.part4.graduation_work.Hospital

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aop.part4.graduation_work.R
import com.example.aop.part4.graduation_work.data.Documents
import com.example.aop.part4.graduation_work.data.LocationLatLngEntity
import com.example.aop.part4.graduation_work.databinding.HospitalInfoBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HospitalInfo: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: HospitalInfoBinding
    private lateinit var mgoogleMap: GoogleMap
    private lateinit var data: Documents
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HospitalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = intent.getParcelableExtra<Documents>("data")!!
        val supportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
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

    companion object{
        const val CAMERA_ZOOM_LEVEL = 17f
    }
}