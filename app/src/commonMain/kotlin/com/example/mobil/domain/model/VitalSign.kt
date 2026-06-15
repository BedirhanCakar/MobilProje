package com.example.mobil.domain.model

data class VitalSign(
    val id: String,
    val userId: String,
    val pulse: Int,
    val systolicBp: Int,
    val diastolicBp: Int,
    val bloodSugar: Double,
    val measuredAt: Long,
    val isSynced: Boolean,
    val updatedAt: Long
)
