package com.example.mobil.data.remote

import com.example.mobil.data.model.Ihbar
import com.example.mobil.data.model.Kutu
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("kutular")
    suspend fun getKutular(): List<Kutu>

    @POST("ihbarlar")
    suspend fun sendIhbar(@Body ihbar: Ihbar): Ihbar
}
