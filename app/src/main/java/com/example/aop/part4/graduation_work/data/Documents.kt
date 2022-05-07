package com.example.aop.part4.graduation_work.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Documents(
    var id: String = "",
    var place_name: String = "",
    var category_name: String = "",
    var category_group_code: String = "",
    var category_group_name: String = "",
    var phone: String = "",
    var address_name: String = "",
    var road_address_name: String = "",
    var x: String = "",
    var y: String = "",
    var place_url: String = "",
    var distance: String = ""
): Parcelable
