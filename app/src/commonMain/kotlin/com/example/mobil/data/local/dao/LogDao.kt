package com.example.mobil.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mobil.data.local.entity.AppLogEntity

@Dao
interface LogDao {
    @Insert
    suspend fun insertLog(log: AppLogEntity)

    @Query("SELECT * FROM app_logs ORDER BY timestamp DESC LIMIT 1000")
    suspend fun getRecentLogs(): List<AppLogEntity>

    @Query("DELETE FROM app_logs WHERE timestamp < :threshold")
    suspend fun clearOldLogs(threshold: Long)

    @Query("DELETE FROM app_logs")
    suspend fun deleteAllLogs()
}
