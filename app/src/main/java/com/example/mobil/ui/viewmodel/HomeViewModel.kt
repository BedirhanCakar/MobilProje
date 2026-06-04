package com.example.mobil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.data.model.Kutu
import com.example.mobil.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _kutular = MutableStateFlow<List<Kutu>>(emptyList())
    val kutular: StateFlow<List<Kutu>> = _kutular

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _selectedLocation = MutableStateFlow<com.google.android.gms.maps.model.LatLng?>(null)
    val selectedLocation: StateFlow<com.google.android.gms.maps.model.LatLng?> = _selectedLocation

    init {
        fetchKutular()
    }

    fun setSelectedLocation(latLng: com.google.android.gms.maps.model.LatLng?) {
        _selectedLocation.value = latLng
    }

    fun fetchKutular() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Backend bağlandığında aktif edilecek
                // val result = RetrofitClient.apiService.getKutular()
                // _kutular.value = result
                
                // Demo verisi kaldırıldı (Kullanıcı isteği üzerine)
                _kutular.value = emptyList()
            } catch (e: Exception) {
                // Hata yönetimi eklenebilir
            } finally {
                _isLoading.value = false
            }
        }
    }
}
