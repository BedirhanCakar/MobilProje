package com.example.mobil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
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

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Hoş Geldiniz",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "Giriş yaparak çevreyi korumaya başlayın",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
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

                Button(
                    onClick = { viewModel.login(email, sifre) },
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
                        Text("Giriş Yap")
                    }
                }

                TextButton(onClick = onNavigateToRegister) {
                    Text("Hesabınız yok mu? Kayıt Olun")
                }
            }
        }
    }
}
