package com.example.mobil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobil.ui.viewmodel.AuthState
import com.example.mobil.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var ad by remember { mutableStateOf("") }
    var soyad by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var sifre by remember { mutableStateOf("") }
    var sifreTekrar by remember { mutableStateOf("") }
    
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            Toast.makeText(context, "Kayıt Başarılı! Giriş yapabilirsiniz.", Toast.LENGTH_SHORT).show()
            onNavigateToLogin()
            viewModel.resetState()
        } else if (authState is AuthState.Error) {
            Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            viewModel.resetState()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Kayıt Ol") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = ad,
                onValueChange = { ad = it },
                label = { Text("Ad") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = soyad,
                onValueChange = { soyad = it },
                label = { Text("Soyad") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail (@gmail.com)") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = sifre,
                onValueChange = { sifre = it },
                label = { Text("Şifre (Min 8 Karakter)") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = sifreTekrar,
                onValueChange = { sifreTekrar = it },
                label = { Text("Şifre Tekrar") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.register(ad, soyad, email, sifre, sifreTekrar) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp))
                else Text("Kayıt Ol")
            }
            TextButton(onClick = onNavigateToLogin) {
                Text("Zaten hesabınız var mı? Giriş Yapın")
            }
        }
    }
}
