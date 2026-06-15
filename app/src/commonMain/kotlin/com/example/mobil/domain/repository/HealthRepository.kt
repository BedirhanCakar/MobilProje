package com.example.mobil.domain.repository

import com.example.mobil.domain.model.Appointment
import com.example.mobil.domain.model.UserProfile
import com.example.mobil.domain.model.VitalSign
import kotlinx.coroutines.flow.Flow

interface HealthRepository {
    // User Profile
    fun getUserProfile(id: String): Flow<UserProfile?>
    suspend fun saveUserProfile(profile: UserProfile)

    // Vital Signs
    fun getVitalSigns(userId: String): Flow<List<VitalSign>>
    suspend fun addVitalSign(vitalSign: VitalSign)

    // Appointments
    fun getAppointments(userId: String): Flow<List<Appointment>>
    suspend fun scheduleAppointment(appointment: Appointment)
    suspend fun updateAppointment(appointment: Appointment)
}
