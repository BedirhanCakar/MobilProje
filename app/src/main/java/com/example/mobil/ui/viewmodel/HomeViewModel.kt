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

    // Kullanıcının eklediği işaretçiler listesi
    private val _userMarkers = MutableStateFlow<List<LatLng>>(emptyList())
    val userMarkers: StateFlow<List<LatLng>> = _userMarkers

    // Şu an seçili olan işaretçinin indeksi
    private val _selectedIndex = MutableStateFlow<Int?>(null)
    val selectedIndex: StateFlow<Int?> = _selectedIndex

    init {
        fetchKutular()
    }

    fun addMarker(latLng: LatLng) {
        val currentList = _userMarkers.value.toMutableList()
        currentList.add(latLng)
        _userMarkers.value = currentList
        // Yeni eklenen otomatik seçilsin mi? Kullanıcı tıkladığında seçilsin dediği için null bırakıyoruz.
        _selectedIndex.value = null 
    }

    fun selectMarker(index: Int) {
        _selectedIndex.value = index
    }

    fun deleteSelectedMarker() {
        val index = _selectedIndex.value
        if (index != null && index in _userMarkers.value.indices) {
            val currentList = _userMarkers.value.toMutableList()
            currentList.removeAt(index)
            _userMarkers.value = currentList
            _selectedIndex.value = null // Silindikten sonra seçimi sıfırla
        }
    }

    fun clearSelection() {
        _selectedIndex.value = null
    }

    fun fetchKutular() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _kutular.value = emptyList()
            } catch (e: Exception) {
                // Hata yönetimi
            } finally {
                _isLoading.value = false
            }
        }
    }
}
