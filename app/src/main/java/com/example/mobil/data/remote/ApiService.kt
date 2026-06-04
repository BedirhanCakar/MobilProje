package com.example.mobil.data.remote

import com.example.mobil.data.model.Ihbar
import com.example.mobil.data.model.Kutu
import com.example.mobil.data.model.LoginRequest
import com.example.mobil.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    // Örnek bir Mock endpoint'i (Harita verileri için)
    @GET("8373b5df-13c5-430c-843e-a131b78297b4")
    suspend fun getKutular(): List<Kutu>

    // Form gönderimi için örnek endpoint
    @POST("ihbarlar")
    suspend fun sendIhbar(@Body ihbar: Ihbar): Ihbar

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): User

    @POST("auth/register")
    suspend fun register(@Body user: User): User
}
