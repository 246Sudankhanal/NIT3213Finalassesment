package com.example.nit3213.data.model

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)

data class AuthResponse(
    @SerializedName("keypass") val keypass: String
)

data class DashboardResponse(
    @SerializedName("entities") val entities: List<Entity>,
    @SerializedName("entityTotal") val entityTotal: Int
)

data class Entity(
    @SerializedName("deviceName") val deviceName: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("operatingSystem") val operatingSystem: String,
    @SerializedName("releaseYear") val releaseYear: Int,
    @SerializedName("description") val description: String
)
