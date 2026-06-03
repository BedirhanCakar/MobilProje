package com.example.mobil.data.remote

import com.example.mobil.data.model.Ihbar
import com.example.mobil.data.model.Kutu
import com.example.mobil.data.model.LoginRequest
import com.example.mobil.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("kutular")
    suspend fun getKutular(): List<Kutu>

    @POST("ihbarlar")
    suspend fun sendIhbar(@Body ihbar: Ihbar): Ihbar

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): User

    @POST("auth/register")
    suspend fun register(@Body user: User): User
}
