package com.example.aop.part4.graduation_work.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserHealth (
    val id : String = "",   //아이디
    val date : String = "", //날짜
    val height : String = "",   //키
    val weight : String = "",   //몸무게
    val things : String = "",   //선호 도구
    val part : String = ""      //선호 부위
) : Parcelable