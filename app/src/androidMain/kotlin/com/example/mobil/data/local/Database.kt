package com.example.mobil.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual fun getDatabaseBuilder(ctx: Any?): RoomDatabase.Builder<VivolanceDatabase> {
    val appContext = (ctx as Context).applicationContext
    val dbFile = appContext.getDatabasePath("vivolance.db")
    return Room.databaseBuilder<VivolanceDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
