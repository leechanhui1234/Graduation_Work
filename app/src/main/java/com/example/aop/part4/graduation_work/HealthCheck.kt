package com.example.aop.part4.graduation_work

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.aop.part4.graduation_work.Url.Url
import com.example.aop.part4.graduation_work.data.UserHealth
import com.example.aop.part4.graduation_work.data.UserHealthCheck
import com.example.aop.part4.graduation_work.databinding.HealthCheckBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.time.LocalDate

class HealthCheck : AppCompatActivity() {

    private lateinit var binding : HealthCheckBinding
    private var thing_list : String = ""
    private var part_list : String = ""

    private var count : Int = 0
    private var list1 = mutableListOf<String>() //도구 리스트
    private var list2 = mutableListOf<String>() //부위 리스트
    private var value : String = ""     //성별
    private var age : Int = 0           //나이
    private var id : String = ""        //아이디
    private var ageValue : String = ""
    private var height : String = ""    //키
    private var weight : String = ""    //몸무게

    lateinit var Date : LocalDate

    var database = Firebase.database.reference.child("health")
    var database_select = Firebase.database.reference.child("health_select")
    private val list = mutableListOf<UserHealth>()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HealthCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controlData()

        Date = LocalDate.now()

        age = intent.getIntExtra("age", 0)
        value = intent.getStringExtra("value") ?: ""
        id = intent.getStringExtra("id") ?: ""

        val sharedPreferences = getSharedPreferences("age", Context.MODE_PRIVATE)
        val sharedPreferences2 = getSharedPreferences("value", Context.MODE_PRIVATE)
        val sharedPreferences3 = getSharedPreferences("id", Context.MODE_PRIVATE)

        if (age == 0) {
            age = sharedPreferences.getInt("age", 0)
        } else {
            sharedPreferences.edit {
                this.putInt("age", age)
                commit()
            }
        }

        if (value.isNullOrEmpty()) {
            value = sharedPreferences2.getString("value", "") ?: ""
        } else {
            sharedPreferences2.edit {
                this.putString("value", value)
                commit()
            }
        }

        if (id.isNullOrEmpty()) {
            id = sharedPreferences3.getString("id", "") ?: ""
        } else {
            sharedPreferences3.edit {
                this.putString("id", id)
                commit()
            }
        }

        with(binding) {
            reset.setOnClickListener {
                thing1.isChecked = false
                thing2.isChecked = false
                thing3.isChecked = false
                thing4.isChecked = false
                thing5.isChecked = false
                thing6.isChecked = false
                thing7.isChecked = false
                thing8.isChecked = false
                thing9.isChecked = false
                thing10.isChecked = false
                thing11.isChecked = false
                thing12.isChecked = false
                thing13.isChecked = false
                thing14.isChecked = false
                thing15.isChecked = false
                thing16.isChecked = false

                part1.isChecked = false
                part2.isChecked = false
                part3.isChecked = false
                part4.isChecked = false
                part5.isChecked = false
                part6.isChecked = false
                part7.isChecked = false
                part8.isChecked = false
                part9.isChecked = false
                part10.isChecked = false
                part11.isChecked = false
                part12.isChecked = false
                part13.isChecked = false
                part14.isChecked = false
                part15.isChecked = false
                part16.isChecked = false
                part17.isChecked = false
                part18.isChecked = false
                part19.isChecked = false
                part20.isChecked = false
            }

            backbtn.setOnClickListener {
                finish()
            }

            healthSave.setOnClickListener {

                list1.clear()

                if(thing1.isChecked) list1.add(thing1.text.toString())
                if(thing2.isChecked) list1.add(thing2.text.toString())
                if(thing3.isChecked) list1.add(thing3.text.toString())
                if(thing4.isChecked) list1.add(thing4.text.toString())
                if(thing5.isChecked) list1.add(thing5.text.toString())
                if(thing6.isChecked) list1.add(thing6.text.toString())
                if(thing7.isChecked) list1.add(thing7.text.toString())
                if(thing8.isChecked) list1.add(thing8.text.toString())
                if(thing9.isChecked) list1.add(thing9.text.toString())
                if(thing10.isChecked) list1.add(thing10.text.toString())
                if(thing11.isChecked) list1.add(thing11.text.toString())
                if(thing12.isChecked) list1.add(thing12.text.toString())
                if(thing13.isChecked) list1.add(thing13.text.toString())
                if(thing14.isChecked) list1.add(thing14.text.toString())
                if(thing15.isChecked) list1.add(thing15.text.toString())
                if(thing16.isChecked) list1.add(thing16.text.toString())

                list2.clear()
                if(part1.isChecked) list2.add(part1.text.toString())
                if(part2.isChecked) list2.add(part2.text.toString())
                if(part3.isChecked) list2.add(part3.text.toString())
                if(part4.isChecked) list2.add(part4.text.toString())
                if(part5.isChecked) list2.add(part5.text.toString())
                if(part6.isChecked) list2.add(part6.text.toString())
                if(part7.isChecked) list2.add(part7.text.toString())
                if(part8.isChecked) list2.add(part8.text.toString())
                if(part9.isChecked) list2.add(part9.text.toString())
                if(part10.isChecked) list2.add(part10.text.toString())
                if(part11.isChecked) list2.add(part11.text.toString())
                if(part12.isChecked) list2.add(part12.text.toString())
                if(part13.isChecked) list2.add(part13.text.toString())
                if(part14.isChecked) list2.add(part14.text.toString())
                if(part15.isChecked) list2.add(part15.text.toString())
                if(part16.isChecked) list2.add(part16.text.toString())
                if(part17.isChecked) list2.add(part17.text.toString())
                if(part18.isChecked) list2.add(part18.text.toString())
                if(part19.isChecked) list2.add(part19.text.toString())
                if(part20.isChecked) list2.add(part20.text.toString())

                thing_list = ""

                for (i in 0 until list1.size step 1){
                    if (i == list1.size - 1) thing_list += list1[i]
                    else thing_list = thing_list + list1[i] + ","
                }

                part_list = ""
                count = 0
                (0 until list2.size step 1).forEach { i ->
                    part_list = if(i == list2.size - 1) part_list + list2[i]
                    else part_list + list2[i] + ","
                    count++
                }

                value = if (value == "남성") "M"
                else "F"

                ageValue = when {
                    age < 20 -> "10대"
                    age < 30 -> "20대"
                    age < 40 -> "30대"
                    age < 50 -> "40대"
                    age < 60 -> "50대"
                    age < 70 -> "60대"
                    else -> "70대 이상"
                }

                height = userHeight.text.toString()
                weight = userWeight.text.toString()

                if(height.isNullOrEmpty() || weight.isNullOrEmpty()){
                    Toast.makeText(applicationContext, "키 또는 몸무게를 입력하지 않으셨습니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (count < 3) {
                    Toast.makeText(applicationContext, "운동 부위는 3개 이상 골라주세요.", Toast.LENGTH_SHORT).show()
                }

                else {
                    progress.visibility = View.VISIBLE
                    loading.visibility = View.VISIBLE
                    reset.visibility = View.GONE
                    healthSave.visibility = View.GONE
                    view2.visibility = View.GONE
                    view3.visibility = View.GONE
                    view4.visibility = View.GONE
                    val text = "$value|$ageValue|$part_list|$thing_list|$height|$weight"
                    database.child(id!!).push().setValue(UserHealth(id, Date.toString(), height, weight, thing_list, part_list))
                    connectServer(text)
                }
            }
        }
    }

    private fun connectServer(text: String) {
        val postUrl = Url.recommendUrl

        val mediaType = "text/plain".toMediaTypeOrNull()
        val postBody = text.toRequestBody(mediaType)

        postRequest(postUrl, postBody)
    }

    private fun postRequest(postUrl: String, postBody: RequestBody){
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(postUrl)
            .post(postBody)
            .build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @SuppressLint("CutPasteId")
            override fun onResponse(call: Call, response: Response) {
                val data = response.body!!.string()
                runOnUiThread {

                    //로딩 바
                    binding.progress.visibility = View.GONE
                    binding.loading.visibility = View.GONE
                    binding.reset.visibility = View.VISIBLE
                    binding.healthSave.visibility = View.VISIBLE
                    binding.view2.visibility = View.VISIBLE
                    binding.view3.visibility = View.VISIBLE
                    binding.view4.visibility = View.VISIBLE

                    val d = data.split("|")
                    var pre_data: List<String>
                    var in_data: List<String>
                    var post_data: List<String>
                    var preurl: List<String>
                    var inurl: List<String>
                    var posturl: List<String>

                    try{
                        pre_data = d[0].split(",")
                        in_data = d[1].split(",")
                        post_data = d[2].split(",")
                        preurl = d[3].split(",")
                        inurl = d[4].split(",")
                        posturl = d[5].split(",")
                    }catch(e: Exception){
                        Toast.makeText(this@HealthCheck, "추천 데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                        return@runOnUiThread
                    }

                    val dialog = AlertDialog.Builder(this@HealthCheck)
                    val inflater = layoutInflater
                    val customView = inflater.inflate(R.layout.custom_dialog, null)

                    //추천 목록 띄우기
                    customView.findViewById<TextView>(R.id.pre_item1).text = pre_data[0]
                    customView.findViewById<TextView>(R.id.pre_item2).text = pre_data[1]
                    customView.findViewById<TextView>(R.id.pre_item3).text = pre_data[2]

                    customView.findViewById<TextView>(R.id.in_item1).text = in_data[0]
                    customView.findViewById<TextView>(R.id.in_item2).text = in_data[1]
                    customView.findViewById<TextView>(R.id.in_item3).text = in_data[2]

                    customView.findViewById<TextView>(R.id.post_item1).text = post_data[0]
                    customView.findViewById<TextView>(R.id.post_item2).text = post_data[1]
                    customView.findViewById<TextView>(R.id.post_item3).text = post_data[2]
                    dialog.setView(customView)
                        .setTitle("추천 목록")

                    val Alertdialog = dialog.show()

                    val yesButton = customView.findViewById<Button>(R.id.yes)
                    val noButton = customView.findViewById<Button>(R.id.no)

                    yesButton.setOnClickListener {

                        val group1 = customView.findViewById<RadioGroup>(R.id.precheck)
                        val group2 = customView.findViewById<RadioGroup>(R.id.incheck)
                        val group3 = customView.findViewById<RadioGroup>(R.id.postcheck)

                        var predata: String? = null
                        var indata: String? = null
                        var postdata: String? = null
                        var pre_url: String? = null
                        var in_url: String? = null
                        var post_url: String? = null

                        when(group1.checkedRadioButtonId) {
                            R.id.pre_item1 -> {
                                predata = customView.findViewById<TextView>(R.id.pre_item1).text.toString()
                                pre_url = preurl[0]
                            }
                            R.id.pre_item2 -> {
                                predata = customView.findViewById<TextView>(R.id.pre_item2).text.toString()
                                pre_url = preurl[1]
                            }
                            R.id.pre_item3 -> {
                                predata = customView.findViewById<TextView>(R.id.pre_item3).text.toString()
                                pre_url = preurl[2]
                            }
                        }

                        when(group2.checkedRadioButtonId) {
                            R.id.in_item1 -> {
                                indata = customView.findViewById<TextView>(R.id.in_item1).text.toString()
                                in_url = inurl[0]
                            }
                            R.id.in_item2 -> {
                                indata = customView.findViewById<TextView>(R.id.in_item2).text.toString()
                                in_url = inurl[1]
                            }
                            R.id.in_item3 -> {
                                indata = customView.findViewById<TextView>(R.id.in_item3).text.toString()
                                in_url = inurl[2]
                            }
                        }

                        when(group3.checkedRadioButtonId) {
                            R.id.post_item1 -> {
                                postdata = customView.findViewById<TextView>(R.id.post_item1).text.toString()
                                post_url = posturl[0]
                            }
                            R.id.post_item2 -> {
                                postdata = customView.findViewById<TextView>(R.id.post_item2).text.toString()
                                post_url = posturl[1]
                            }
                            R.id.post_item3 -> {
                                postdata = customView.findViewById<TextView>(R.id.post_item3).text.toString()
                                post_url = posturl[2]
                            }
                        }

                        if (predata.isNullOrEmpty() || indata.isNullOrEmpty() || postdata.isNullOrEmpty()) {
                            Toast.makeText(this@HealthCheck, "데이터를 선택해주세요", Toast.LENGTH_SHORT).show()
                        } else {
                            //DB에 health_select 이름으로 저장
                            //database_select.child(id!!).push().setValue(UserHealthCheck(id, Date.toString(), predata.toString(), indata.toString(), postdata.toString(), 0))
                            val intent = Intent(this@HealthCheck, HealthView::class.java)
                            intent.putExtra("predata", predata)
                            intent.putExtra("indata", indata)
                            intent.putExtra("postdata", postdata)
                            intent.putExtra("preurl", pre_url)
                            intent.putExtra("inurl", in_url)
                            intent.putExtra("posturl", post_url)
                            intent.putExtra("id", id)
                            startActivity(intent)
                            finish()
                        }
                    }

                    noButton.setOnClickListener {
                        Alertdialog!!.dismiss()
                    }

                }
                Log.d("RESPONSE", data)
            }
        })
    }

    //필요없어 보이지만 일단 스킵
    private fun controlData() {
        database.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val item = snapshot.getValue(UserHealth::class.java)
                val key = snapshot.key

                val list = UserHealth(id!!, item!!.date, item!!.height, item!!.weight, item!!.things, item!!.part)

                binding.userHeight.setText(list.height)
                binding.userWeight.setText(list.weight)
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
}