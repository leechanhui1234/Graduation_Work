package com.example.aop.part4.graduation_work.Board.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatKeyModel(
    val userId: String = "",
    val title: String = "",
    val content: String = "",
    val time: String = "",
    val key: String = ""
): Parcelable
