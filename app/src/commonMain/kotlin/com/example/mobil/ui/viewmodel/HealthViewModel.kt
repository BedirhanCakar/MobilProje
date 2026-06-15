package com.example.mobil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.data.local.dao.LogDao
import com.example.mobil.data.local.entity.AppLogEntity
import com.example.mobil.domain.model.Appointment
import com.example.mobil.domain.model.UserProfile
import com.example.mobil.domain.model.VitalSign
import com.example.mobil.domain.repository.HealthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class HealthUiState(
    val isLoading: Boolean = false,
    val userProfile: UserProfile? = null,
    val vitalSigns: List<VitalSign> = emptyList(),
    val appointments: List<Appointment> = emptyList(),
    val errorMessage: String? = null
)

class HealthViewModel(
    private val repository: HealthRepository,
    private val logDao: LogDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HealthUiState())
    val uiState: StateFlow<HealthUiState> = _uiState.asStateFlow()

    init {
        // Not: Gerçek senaryoda userId Auth katmanından gelmelidir.
        loadDashboardData("user_test_001")
    }

    fun loadDashboardData(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            // Profil, Vitaller ve Randevular paralel olarak Flow üzerinden dinleniyor
            try {
                combine(
                    repository.getUserProfile(userId),
                    repository.getVitalSigns(userId),
                    repository.getAppointments(userId)
                ) { profile, vitals, appointments ->
                    HealthUiState(
                        isLoading = false,
                        userProfile = profile,
                        vitalSigns = vitals,
                        appointments = appointments,
                        errorMessage = null
                    )
                }.catch { e ->
                    handleError("Veri yükleme hatası", e)
                }.collect { newState ->
                    _uiState.value = newState
                }
            } catch (e: Exception) {
                handleError("Genel hata", e)
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun addVitalSign(vitalSign: VitalSign) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addVitalSign(vitalSign)
            } catch (e: Exception) {
                handleError("Vital bulgu eklenirken hata oluştu", e)
            }
        }
    }

    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.scheduleAppointment(appointment)
            } catch (e: Exception) {
                handleError("Randevu oluşturulurken hata oluştu", e)
            }
        }
    }

    private suspend fun handleError(message: String, throwable: Throwable) {
        val finalMessage = "$message: ${throwable.message}"
        _uiState.update { it.copy(errorMessage = finalMessage, isLoading = false) }
        
        // Veritabanına log yazma işlemi
        logDao.insertLog(
            AppLogEntity(
                tag = "HealthViewModel",
                message = finalMessage,
                timestamp = System.currentTimeMillis(),
                level = "ERROR"
            )
        )
    }
}
