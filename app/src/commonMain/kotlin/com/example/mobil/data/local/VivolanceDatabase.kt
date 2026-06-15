package com.example.mobil.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobil.data.local.dao.AppointmentDao
import com.example.mobil.data.local.dao.LogDao
import com.example.mobil.data.local.dao.UserDao
import com.example.mobil.data.local.dao.VitalSignDao
import com.example.mobil.data.local.entity.AppointmentEntity
import com.example.mobil.data.local.entity.AppLogEntity
import com.example.mobil.data.local.entity.UserProfileEntity
import com.example.mobil.data.local.entity.VitalSignEntity

@Database(
    entities = [
        UserProfileEntity::class,
        VitalSignEntity::class,
        AppointmentEntity::class,
        AppLogEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class VivolanceDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun vitalSignDao(): VitalSignDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun logDao(): LogDao
}
