package com.example.mobil.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(ctx: Any?): RoomDatabase.Builder<VivolanceDatabase> {
    val dbFilePath = NSHomeDirectory() + "/vivolance.db"
    return Room.databaseBuilder<VivolanceDatabase>(
        name = dbFilePath,
        factory = { VivolanceDatabase::class.instantiateImpl() }
    )
}
