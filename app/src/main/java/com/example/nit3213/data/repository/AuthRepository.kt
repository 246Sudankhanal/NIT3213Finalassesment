package com.example.nit3213.data.repository

import com.example.nit3213.data.api.ApiService
import com.example.nit3213.data.model.AuthRequest
import com.example.nit3213.data.model.AuthResponse
import com.example.nit3213.data.model.DashboardResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun login(request: AuthRequest): Response<AuthResponse> {
        return apiService.login(request)
    }

    suspend fun getDashboard(keypass: String): Response<DashboardResponse> {
        return apiService.getDashboard(keypass)
    }
}
