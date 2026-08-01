package com.yashir.vehicledashboard.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface DashboardApiService {
    @POST("dashboard/update")
    suspend fun updateDashboard(@Body data: DashboardRequest): DashboardResponse
}

object DashboardApi {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    val service: DashboardApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DashboardApiService::class.java)
    }
}