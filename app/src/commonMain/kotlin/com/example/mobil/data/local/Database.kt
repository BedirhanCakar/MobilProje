package com.example.mobil.data.local

import androidx.room.RoomDatabase

expect fun getDatabaseBuilder(ctx: Any? = null): RoomDatabase.Builder<VivolanceDatabase>
