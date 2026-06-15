package com.example.mobil.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "appointments",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class AppointmentEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val doctorName: String,
    val department: String,
    val appointmentAt: Long,
    val status: String,
    val isSynced: Boolean,
    val updatedAt: Long
)
