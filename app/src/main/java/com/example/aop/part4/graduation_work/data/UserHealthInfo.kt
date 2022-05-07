package com.example.aop.part4.graduation_work.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserHealthInfo(
    @PrimaryKey val uid: Int?,
    @ColumnInfo(name = "userId") val userId: String?,
    @ColumnInfo(name = "pre_select") val pre_select: String?,
    @ColumnInfo(name = "in_select") val in_select: String?,
    @ColumnInfo(name = "post_select") val post_select: String?,
    @ColumnInfo(name = "record") val record: Int?
)
