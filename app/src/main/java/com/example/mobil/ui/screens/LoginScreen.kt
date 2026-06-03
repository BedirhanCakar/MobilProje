package com.example.mobil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
fun LoginScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var sifre by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onNavigateToDashboard()
            viewModel.resetState()
        } else if (authState is AuthState.Error) {
            Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            viewModel.resetState()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Giriş Yap") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                label = { Text("Şifre") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.login(email, sifre) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) CircularProgressIndicator(size = 24.dp)
                else Text("Giriş Yap")
            }
            TextButton(onClick = onNavigateToRegister) {
                Text("Hesabınız yok mu? Kayıt Olun")
            }
        }
    }
}

@Composable
fun CircularProgressIndicator(size: androidx.compose.ui.unit.Dp) {
    androidx.compose.material3.CircularProgressIndicator(
        modifier = Modifier.size(size),
        strokeWidth = 2.dp,
        color = MaterialTheme.colorScheme.onPrimary
    )
}
