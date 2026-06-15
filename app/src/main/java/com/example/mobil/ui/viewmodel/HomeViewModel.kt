package com.example.mobil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.data.model.Kutu
import com.example.mobil.data.model.UserMarker
import com.example.mobil.data.remote.RetrofitClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _kutular = MutableStateFlow<List<Kutu>>(emptyList())
    val kutular: StateFlow<List<Kutu>> = _kutular

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _userMarkers = MutableStateFlow<List<UserMarker>>(emptyList())
    val userMarkers: StateFlow<List<UserMarker>> = _userMarkers

    private val _selectedMarkerId = MutableStateFlow<String?>(null)
    val selectedMarkerId: StateFlow<String?> = _selectedMarkerId

    init {
        fetchKutular()
    }

    fun addMarker(latLng: LatLng, wasteType: String) {
        val currentList = _userMarkers.value.toMutableList()
        currentList.add(UserMarker(position = latLng, wasteType = wasteType))
        _userMarkers.value = currentList
    }

    fun selectMarker(id: String) {
        _selectedMarkerId.value = id
    }

    fun deleteSelectedMarker() {
        val id = _selectedMarkerId.value
        if (id != null) {
            val currentList = _userMarkers.value.toMutableList()
            currentList.removeAll { it.id == id }
            _userMarkers.value = currentList
            _selectedMarkerId.value = null
        }
    }

    fun clearSelection() {
        _selectedMarkerId.value = null
    }

    fun fetchKutular() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = RetrofitClient.apiService.getKutular()
                _kutular.value = result
            } catch (e: Exception) {
                _kutular.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
