package com.example.bumangapp.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

data class User(
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("is_premium") val isPremium: Any? // Handle flexible type (0/1 or true/false)
) {
    // Helper para obtener el booleano real sin importar lo que mande SQLite
    fun getIsPremium(): Boolean {
        return when (isPremium) {
            is Boolean -> isPremium
            is Number -> isPremium.toInt() == 1
            is String -> isPremium == "1" || isPremium.lowercase() == "true"
            else -> false
        }
    }
}

data class AuthResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    val user: User
)

data class BusRouteResponse(
    val id: Int,
    val name: String,
    val color: String?,
    val points: String
)

data class BusResponse(
    val id: Int,
    val name: String,
    @SerializedName("bus_route_id") val routeId: Int,
    @SerializedName("driver_name") val driverName: String,
    val plate: String,
    @SerializedName("current_lat") val currentLat: Double?,
    @SerializedName("current_lng") val currentLng: Double?,
    @SerializedName("passenger_count") val passengerCount: Int
)

interface ApiService {
    @POST("register")
    suspend fun register(@Body data: Map<String, String>): Response<AuthResponse>

    @POST("login")
    suspend fun login(@Body data: Map<String, String>): Response<AuthResponse>

    @GET("user")
    suspend fun getUser(@Header("Authorization") token: String): Response<User>

    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>

    @GET("routes")
    suspend fun getRoutes(@Header("Authorization") token: String): Response<List<BusRouteResponse>>

    @GET("buses")
    suspend fun getBuses(@Header("Authorization") token: String): Response<List<BusResponse>>
}
