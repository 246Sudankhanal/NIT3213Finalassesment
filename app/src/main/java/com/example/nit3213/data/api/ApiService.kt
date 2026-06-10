package com.example.nit3213.data.api

import com.example.nit3213.data.model.AuthRequest
import com.example.nit3213.data.model.AuthResponse
import com.example.nit3213.data.model.DashboardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/sydney/auth")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @GET("/dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): Response<DashboardResponse>
}
