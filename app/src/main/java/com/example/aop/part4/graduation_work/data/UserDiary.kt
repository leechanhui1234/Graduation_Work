package com.example.aop.part4.graduation_work.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDiary (
    val Diary : String,
    val Day : String
) : Parcelable