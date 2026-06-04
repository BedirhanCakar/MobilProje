package com.example.mobil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.data.model.Kutu
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _kutular = MutableStateFlow<List<Kutu>>(emptyList())
    val kutular: StateFlow<List<Kutu>> = _kutular

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Çoklu işaretçi için liste yapısına geçildi
    private val _selectedLocations = MutableStateFlow<List<LatLng>>(emptyList())
    val selectedLocations: StateFlow<List<LatLng>> = _selectedLocations

    init {
        fetchKutular()
    }

    fun addSelectedLocation(latLng: LatLng) {
        val currentList = _selectedLocations.value.toMutableList()
        currentList.add(latLng)
        _selectedLocations.value = currentList
    }

    fun removeLastLocation() {
        val currentList = _selectedLocations.value.toMutableList()
        if (currentList.isNotEmpty()) {
            currentList.removeAt(currentList.size - 1)
            _selectedLocations.value = currentList
        }
    }

    fun fetchKutular() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Demo verisi boşaltıldı
                _kutular.value = emptyList()
            } catch (e: Exception) {
                // Hata yönetimi
            } finally {
                _isLoading.value = false
            }
        }
    }
}
