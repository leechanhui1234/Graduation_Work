package com.example.aop.part4.graduation_work.Hospital

import com.example.aop.part4.graduation_work.data.RequestSearchKeyword
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {
    @GET("/v2/local/search/keyword.json")
    suspend fun getSearchKeyword(
        @Header("Authorization") key: String,
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("query") query: String,
    ): Response<RequestSearchKeyword>
}