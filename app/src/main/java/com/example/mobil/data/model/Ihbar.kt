package com.example.mobil.data.model

import com.google.gson.annotations.SerializedName

/**
 * IhbarlarTablosu [Id (INT), KutuId (INT), KullaniciNotu (TEXT), Tarih (DATETIME)]
 */
data class Ihbar(
    @SerializedName("Id") val id: Int,
    @SerializedName("KutuId") val kutuId: Int,
    @SerializedName("KullaniciNotu") val kullaniciNotu: String,
    @SerializedName("Tarih") val tarih: String
)
