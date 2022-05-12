package com.example.aop.part4.graduation_work.Hospital

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop.part4.graduation_work.Hospital.Adapter.HospitalAdapter
import com.example.aop.part4.graduation_work.databinding.HospitalListBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.chat_write.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class HospitalList: AppCompatActivity() {
    private val scope = MainScope()
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null //현재 위치 가져오기
    private var locationManager: LocationManager ?= null
    internal lateinit var mLocationRequest: com.google.android.gms.location.LocationRequest
    private lateinit var binding: HospitalListBinding
    private lateinit var adapter: HospitalAdapter
    var lati: String ?= null
    var longi: String ?= null
    var id: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HospitalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.visibility = android.view.View.GONE
        binding.progress.visibility = android.view.View.VISIBLE
        binding.loading.visibility = android.view.View.VISIBLE

        adapter = HospitalAdapter(onItemClicked = { it ->
            val intent = Intent(this@HospitalList, HospitalInfo::class.java)
            intent.putExtra("data", it)
            intent.putExtra("id", id)
            startActivity(intent)
        })

        id = intent.getStringExtra("id")
        val sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE)

        if(id.isNullOrEmpty()){
            id = sharedPreferences.getString("id", "") ?: ""
        } else{
            sharedPreferences.edit {
                this.putString("id", id)
                commit()
            }
        }

        binding.backbtn.setOnClickListener {
            finish()
        }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        mLocationRequest = com.google.android.gms.location.LocationRequest().apply {
            interval = 1000 * 60 * 60 //업데이트 간격 단위
            fastestInterval = 1000 * 60 * 60 //가장 빠른 업뎃 간격 단위
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY //정확성
            maxWaitTime = 2000 //최대 대기 시간
        }
        initAdapter()
        searchKeyword()
    }

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun initAdapter() {
        with(binding){
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(this@HospitalList)
        }
    }

    private fun searchKeyword(){
        scope.launch {
            if(checkPermissionForLocation(this@HospitalList)){
                startLocationUpdate()
                checkGPS()

                if(lati != null){
                    val data = Repository.getListApi(lati!! ,longi!!, "정신병원")
                    adapter.submitList(data)
                    adapter.notifyDataSetChanged()
                    binding.recycler.visibility = android.view.View.VISIBLE
                    binding.progress.visibility = android.view.View.GONE
                    binding.loading.visibility = android.view.View.GONE
                }   
            }
        }
    }

    private fun checkGPS() {
        if(!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            val dialog = AlertDialog.Builder(this@HospitalList)
                .setTitle("위치 권한 요청")
                .setMessage("위치 권한이 없습니다. 위치 권한을 활성화시켜 주시기 바랍니다.")
                .setPositiveButton("확인"){ dialog, switch ->
                    var intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    startActivity(intent)
                }.setNegativeButton("취소"){ dialog, switch ->
                    Toast.makeText(this@HospitalList, "위치 권한이 없어 해당 기능을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }.show()
        }
    }

    private fun startLocationUpdate() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            return
        }

        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            lati = locationResult.lastLocation.longitude.toString()
            longi = locationResult.lastLocation.latitude.toString()
            searchKeyword()
        }
    }

    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdate()

            } else {
                Toast.makeText(this, "내부 오류 담당자에게 문의 바랍니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object{
        const val REQUEST_PERMISSION_LOCATION = 10
    }
}