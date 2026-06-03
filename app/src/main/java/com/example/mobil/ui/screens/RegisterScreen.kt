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
import androidx.compose.ui.text.font.FontWeight
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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Yeni Hesap Oluştur",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = ad,
                    onValueChange = { ad = it },
                    label = { Text("Ad") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = soyad,
                    onValueChange = { soyad = it },
                    label = { Text("Soyad") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail (@gmail.com)") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = sifre,
                    onValueChange = { sifre = it },
                    label = { Text("Şifre") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !isLoading,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = sifreTekrar,
                    onValueChange = { sifreTekrar = it },
                    label = { Text("Şifre Tekrar") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    enabled = !isLoading,
                    shape = MaterialTheme.shapes.medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.register(ad, soyad, email, sifre, sifreTekrar) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    enabled = !isLoading,
                    shape = MaterialTheme.shapes.medium
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Kayıt Ol")
                    }
                }
                TextButton(onClick = onNavigateToLogin) {
                    Text("Zaten hesabınız var mı? Giriş Yapın")
                }
            }
        }
    }
}
