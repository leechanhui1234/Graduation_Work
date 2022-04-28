package com.example.aop.part4.graduation_work.Hospital
import com.example.aop.part4.graduation_work.Url.Key.key.KakaoKey
import com.example.aop.part4.graduation_work.Url.Url.kakaoUrl
import com.example.aop.part4.graduation_work.data.Documents
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Repository {
    suspend fun getListApi(x: String, y: String, query: String): List<Documents>? {
        val data = kakaoApiService
            .getSearchKeyword(KakaoKey, x, y, query)
            .body()?.documents

        return data
    }

    private val kakaoApiService: KakaoApiService by lazy{
        Retrofit.Builder()
            .baseUrl(kakaoUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}