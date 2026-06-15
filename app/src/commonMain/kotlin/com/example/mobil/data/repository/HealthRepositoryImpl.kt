package com.example.mobil.data.repository

import com.example.mobil.data.local.dao.AppointmentDao
import com.example.mobil.data.local.dao.UserDao
import com.example.mobil.data.local.dao.VitalSignDao
import com.example.mobil.data.mapper.toDomain
import com.example.mobil.data.mapper.toEntity
import com.example.mobil.domain.model.Appointment
import com.example.mobil.domain.model.UserProfile
import com.example.mobil.domain.model.VitalSign
import com.example.mobil.domain.repository.HealthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class HealthRepositoryImpl(
    private val userDao: UserDao,
    private val vitalSignDao: VitalSignDao,
    private val appointmentDao: AppointmentDao
) : HealthRepository {

    override fun getUserProfile(id: String): Flow<UserProfile?> {
        return userDao.getUserById(id).map { it?.toDomain() }
    }

    override suspend fun saveUserProfile(profile: UserProfile) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(profile.toEntity())
        }
    }

    override fun getVitalSigns(userId: String): Flow<List<VitalSign>> {
        return vitalSignDao.getVitalSignsByUser(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addVitalSign(vitalSign: VitalSign) {
        withContext(Dispatchers.IO) {
            vitalSignDao.insertVitalSign(vitalSign.toEntity())
        }
    }

    override fun getAppointments(userId: String): Flow<List<Appointment>> {
        return appointmentDao.getAppointmentsByUser(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun scheduleAppointment(appointment: Appointment) {
        withContext(Dispatchers.IO) {
            appointmentDao.insertAppointment(appointment.toEntity())
        }
    }

    override suspend fun updateAppointment(appointment: Appointment) {
        withContext(Dispatchers.IO) {
            appointmentDao.updateAppointment(appointment.toEntity())
        }
    }
}
