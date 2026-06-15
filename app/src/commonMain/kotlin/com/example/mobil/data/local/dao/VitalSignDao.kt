package com.example.mobil.data.local.dao

import androidx.room.*
import com.example.mobil.data.local.entity.VitalSignEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalSignDao {
    @Query("SELECT * FROM vital_signs WHERE userId = :userId ORDER BY measuredAt DESC")
    fun getVitalSignsByUserId(userId: String): Flow<List<VitalSignEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVitalSign(vitalSign: VitalSignEntity)

    @Update
    suspend fun updateVitalSign(vitalSign: VitalSignEntity)

    @Delete
    suspend fun deleteVitalSign(vitalSign: VitalSignEntity)

    @Query("SELECT * FROM vital_signs WHERE isSynced = 0")
    suspend fun getUnsyncedVitalSigns(): List<VitalSignEntity>
}
