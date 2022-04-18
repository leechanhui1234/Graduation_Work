package com.example.aop.part4.graduation_work.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDiary (
    val title : String = "",
    val diary : String = "",
    val day : String = ""
) : Parcelable