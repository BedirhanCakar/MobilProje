package com.example.mobil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.data.model.Ihbar
import com.example.mobil.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NotificationViewModel : ViewModel() {
    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    private val _submitSuccess = MutableStateFlow<Boolean?>(null)
    val submitSuccess: StateFlow<Boolean?> = _submitSuccess

    fun sendIhbar(kutuId: Int, note: String) {
        viewModelScope.launch {
            _isSubmitting.value = true
            try {
                val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                val ihbar = Ihbar(0, kutuId, note, currentDate)
                
                // Backend bağlandığında aktif edilecek
                // RetrofitClient.apiService.sendIhbar(ihbar)
                
                _submitSuccess.value = true
            } catch (e: Exception) {
                _submitSuccess.value = false
            } finally {
                _isSubmitting.value = false
            }
        }
    }

    fun resetStatus() {
        _submitSuccess.value = null
    }
}
