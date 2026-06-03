package com.example.mobil.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("Id") val id: Int = 0,
    @SerializedName("Ad") val ad: String,
    @SerializedName("Soyad") val soyad: String,
    @SerializedName("Email") val email: String,
    @SerializedName("Sifre") val sifre: String
)

data class LoginRequest(
    @SerializedName("Email") val email: String,
    @SerializedName("Sifre") val sifre: String
)
