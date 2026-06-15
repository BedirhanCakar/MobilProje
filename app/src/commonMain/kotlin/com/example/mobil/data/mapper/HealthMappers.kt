package com.example.mobil.data.mapper

import com.example.mobil.data.local.entity.AppointmentEntity
import com.example.mobil.data.local.entity.UserProfileEntity
import com.example.mobil.data.local.entity.VitalSignEntity
import com.example.mobil.domain.model.Appointment
import com.example.mobil.domain.model.UserProfile
import com.example.mobil.domain.model.VitalSign

// UserProfile Mappers
fun UserProfileEntity.toDomain() = UserProfile(
    id = id,
    name = name,
    email = email,
    createdAt = createdAt,
    isSynced = isSynced,
    updatedAt = updatedAt
)

fun UserProfile.toEntity() = UserProfileEntity(
    id = id,
    name = name,
    email = email,
    createdAt = createdAt,
    isSynced = isSynced,
    updatedAt = updatedAt
)

// VitalSign Mappers
fun VitalSignEntity.toDomain() = VitalSign(
    id = id,
    userId = userId,
    pulse = pulse,
    systolicBp = systolicBp,
    diastolicBp = diastolicBp,
    bloodSugar = bloodSugar,
    measuredAt = measuredAt,
    isSynced = isSynced,
    updatedAt = updatedAt
)

fun VitalSign.toEntity() = VitalSignEntity(
    id = id,
    userId = userId,
    pulse = pulse,
    systolicBp = systolicBp,
    diastolicBp = diastolicBp,
    bloodSugar = bloodSugar,
    measuredAt = measuredAt,
    isSynced = isSynced,
    updatedAt = updatedAt
)

// Appointment Mappers
fun AppointmentEntity.toDomain() = Appointment(
    id = id,
    userId = userId,
    doctorName = doctorName,
    department = department,
    appointmentAt = appointmentAt,
    status = status,
    isSynced = isSynced,
    updatedAt = updatedAt
)

fun Appointment.toEntity() = AppointmentEntity(
    id = id,
    userId = userId,
    doctorName = doctorName,
    department = department,
    appointmentAt = appointmentAt,
    status = status,
    isSynced = isSynced,
    updatedAt = updatedAt
)
