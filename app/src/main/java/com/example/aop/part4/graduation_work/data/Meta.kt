package com.example.aop.part4.graduation_work.data

data class Meta(
    var total_count: Int,
    var pageable_count: Int,
    var is_end: Boolean,
    val same_name: RegionInfo
)
