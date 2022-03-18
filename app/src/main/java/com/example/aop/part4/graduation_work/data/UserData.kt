package com.example.aop.part4.graduation_work.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    val userId: String,
    val userPw: String,
    val userEmail: String,
    val userAge: Int,
    val userValue: String,
    val userName: String
): Parcelable
