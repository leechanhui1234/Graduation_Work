package com.example.aop.part4.graduation_work.More.Fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.aop.part4.graduation_work.Board.model.ChatKeyModel
import com.example.aop.part4.graduation_work.Board.model.ChatModel
import com.example.aop.part4.graduation_work.LoginActivity
import com.example.aop.part4.graduation_work.More.ChangeEmail
import com.example.aop.part4.graduation_work.More.ChangePw
import com.example.aop.part4.graduation_work.More.DeleteUser
import com.example.aop.part4.graduation_work.R
import com.example.aop.part4.graduation_work.data.ImageModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import android.content.ContentResolver as ContentResolver

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentUser : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var userImage: ImageView? = null
    private val storage = Firebase.storage.reference
    private var id: String ?= null
    private var pw: String ?= null
    private var name: String ?= null
    private var email: String ?= null


    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            //var inImage = activity?.getContentResolver()?.openInputStream(intent?.data!!)
            //var img = BitmapFactory.decodeStream(inImage)
            //inImage?.close()
            //userImage?.setImageBitmap(img)
            //Log.e("Main", img.toString())
            Glide.with(requireActivity()).load(intent?.data).into(userImage!!)
            Log.e("Main", intent?.data.toString())
            var time = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            var imageName = "IMAGE_" + id + ".png"
            storage.child("image/${id!!}").child(imageName).putFile(intent?.data!!).addOnSuccessListener {
                userImage?.setImageURI(intent!!.data)
            }
            //userImage?.setImageURI(intent!!.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        userImage = view.findViewById<ImageView>(R.id.userImage)
        userImage!!.setOnClickListener {
            var intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startForResult.launch(intent)
        }
        id = arguments?.getString("id") ?: "null"
        pw = arguments?.getString("pw") ?: "null"
        email = arguments?.getString("email") ?: "null"
        name = arguments?.getString("name") ?: "null"


        storage.child("image/${id!!}").child("IMAGE_${id}.png").downloadUrl.addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                Glide.with(requireActivity())
                    .load(task.result)
                    .into(userImage!!)
            }
        }

        view.findViewById<TextView>(R.id.name).setText(name)
        view.findViewById<TextView>(R.id.email).setText(email)

        val changePw = view.findViewById<TextView>(R.id.changePw)
        val changeEmail = view.findViewById<TextView>(R.id.changeEmail)
        val logout = view.findViewById<TextView>(R.id.logout)
        val Delete = view.findViewById<TextView>(R.id.Delete)

        changePw.setOnClickListener {
            var intent = Intent(activity, ChangePw::class.java)
            intent.putExtra("id", id)
            intent.putExtra("pw", pw)
            startActivity(intent)
        }

        changeEmail.setOnClickListener {
            var intent = Intent(activity, ChangeEmail::class.java)
            intent.putExtra("id", id)
            intent.putExtra("email", email)
            startActivity(intent)
        }

        logout.setOnClickListener {
            AlertDialog.Builder(activity)
                .setMessage("정말로 로그아웃하시겠습니까?")
                .setPositiveButton("확인"){ dialog, view ->
                    var intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                }.setNegativeButton("취소"){ _, _ ->
                }.create()
                .show()
        }

        Delete.setOnClickListener {
            AlertDialog.Builder(activity)
                .setMessage("정말로 회원탈퇴를 하시겠습니까?")
                .setPositiveButton("확인"){ dialog, view ->
                    var intent = Intent(activity, DeleteUser::class.java)
                    intent.putExtra("id", id)
                    intent.putExtra("pw", pw)
                    startActivity(intent)
                }.setNegativeButton("취소"){ _, _ ->
                }.create()
                .show()
        }

        return view
    }

}