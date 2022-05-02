package com.example.aop.part4.graduation_work.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserHealthCheck (
    val id : String = "",   //아이디
    val date : String = "", //날짜
    val pre_select : String = "",    //선택한 준비 운동 종류
    val in_select : String = "",    //선택한 본 운동 종류
    val post_select : String = "",    //선택한 마무리 운동 종류
    val record : Int = 0    //운동 시간
) : Parcelable