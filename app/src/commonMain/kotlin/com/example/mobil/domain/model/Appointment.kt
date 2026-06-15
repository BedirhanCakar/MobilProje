package com.example.mobil.domain.model

data class Appointment(
    val id: String,
    val userId: String,
    val doctorName: String,
    val department: String,
    val appointmentAt: Long,
    val status: String,
    val isSynced: Boolean,
    val updatedAt: Long
)
