package com.example.mobil.data.model

import com.google.android.gms.maps.model.LatLng
import java.util.UUID

data class UserMarker(
    val id: String = UUID.randomUUID().toString(),
    val position: LatLng,
    val wasteType: String
)
