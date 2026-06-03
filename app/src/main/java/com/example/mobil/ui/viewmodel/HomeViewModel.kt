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

    init {
        fetchKutular()
    }

    fun fetchKutular() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Backend bağlandığında aktif edilecek
                // val result = RetrofitClient.apiService.getKutular()
                // _kutular.value = result
                
                // Demo verisi
                _kutular.value = listOf(
                    Kutu(1, "Plastik", 60, 41.0082, 28.9784),
                    Kutu(2, "Kağıt", 30, 41.0182, 28.9884),
                    Kutu(3, "Cam", 85, 41.0282, 28.9684)
                )
            } catch (e: Exception) {
                // Hata yönetimi eklenebilir
            } finally {
                _isLoading.value = false
            }
        }
    }
}
