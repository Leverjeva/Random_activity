package com.example.m16_architecture.data

import com.example.m16_architecture.entity.UsefulActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

private const val BASE_URL = "https://www.boredapi.com"

class UsefulActivitiesRepository @Inject constructor() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val activity: ActivityApiService = retrofit.create(ActivityApiService::class.java)

    suspend fun getUsefulActivity(): UsefulActivity {
        return activity.getActivityInfo()
    }
}

interface ActivityApiService {
    @GET("/api/activity")
    suspend fun getActivityInfo(): UsefulActivityDto
}