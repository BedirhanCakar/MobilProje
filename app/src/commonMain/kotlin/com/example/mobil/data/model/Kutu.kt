package com.example.mobil.data.model

import com.google.gson.annotations.SerializedName

/**
 * KutularTablosu [Id (INT), Tip (VARCHAR), DolulukOrani (INT), Lat (DOUBLE), Lng (DOUBLE)]
 */
data class Kutu(
    @SerializedName("Id") val id: Int,
    @SerializedName("Tip") val tip: String,
    @SerializedName("DolulukOrani") val dolulukOrani: Int,
    @SerializedName("Lat") val lat: Double,
    @SerializedName("Lng") val lng: Double
)
