package com.example.mobil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobil.data.model.LoginRequest
import com.example.mobil.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val nameRegex = Regex("^[a-zA-ZçÇğĞıİöÖşŞüÜ\\s]+$")

    fun login(email: String, sifre: String) {
        if (!email.endsWith("@gmail.com")) {
            _authState.value = AuthState.Error("Sadece @gmail.com adresleri kabul edilir.")
            return
        }
        if (sifre.length < 8) {
            _authState.value = AuthState.Error("Şifre en az 8 karakter olmalıdır.")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Backend entegrasyonu: val user = RetrofitClient.apiService.login(LoginRequest(email, sifre))
                // Demo giriş
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Giriş başarısız: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(ad: String, soyad: String, email: String, sifre: String, sifreTekrar: String) {
        if (!nameRegex.matches(ad) || !nameRegex.matches(soyad)) {
            _authState.value = AuthState.Error("Ad ve Soyad özel karakter içeremez.")
            return
        }
        if (!email.endsWith("@gmail.com")) {
            _authState.value = AuthState.Error("Sadece @gmail.com adresleri kabul edilir.")
            return
        }
        if (sifre.length < 8) {
            _authState.value = AuthState.Error("Şifre en az 8 karakter olmalıdır.")
            return
        }
        if (sifre != sifreTekrar) {
            _authState.value = AuthState.Error("Şifreler eşleşmiyor.")
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newUser = User(ad = ad, soyad = soyad, email = email, sifre = sifre)
                // Backend entegrasyonu: RetrofitClient.apiService.register(newUser)
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Kayıt başarısız: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
