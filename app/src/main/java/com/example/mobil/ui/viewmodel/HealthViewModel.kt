package com.example.mobil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.domain.model.VitalSign
import com.example.mobil.domain.model.VitalType
import com.example.mobil.domain.repository.HealthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class HealthUiState {
    object Loading : HealthUiState()
    data class Success(val vitals: List<VitalSign>) : HealthUiState()
    data class Error(val message: String) : HealthUiState()
}

class HealthViewModel(
    private val repository: HealthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HealthUiState>(HealthUiState.Loading)
    val uiState: StateFlow<HealthUiState> = _uiState.asStateFlow()

    fun loadVitals(userId: String) {
        viewModelScope.launch {
            repository.getVitalSigns(userId)
                .catch { e ->
                    _uiState.value = HealthUiState.Error(e.message ?: "Bilinmeyen hata")
                }
                .collect { vitals ->
                    _uiState.value = HealthUiState.Success(vitals)
                }
        }
    }

    fun addHeartRate(userId: String, value: Double) {
        viewModelScope.launch {
            val vital = VitalSign(
                userId = userId,
                type = VitalType.HEART_RATE,
                value = value,
                unit = "BPM",
                timestamp = System.currentTimeMillis()
            )
            repository.addVitalSign(vital)
        }
    }
}
