package com.example.mobil.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "vital_signs",
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
data class VitalSignEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val pulse: Int,
    val systolicBp: Int,
    val diastolicBp: Int,
    val bloodSugar: Double,
    val measuredAt: Long,
    val isSynced: Boolean,
    val updatedAt: Long
)
