package com.example.aop.part4.graduation_work.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class totalReviewModel(
    val id: String = "",
    val rating: String = "",
    val review: String = "",
    val date: String = "",
    val info: String = ""
)
