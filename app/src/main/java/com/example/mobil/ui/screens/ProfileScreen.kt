package com.example.mobil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Kullanıcı Profili") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = "Bedirhan", style = MaterialTheme.typography.headlineMedium)
            Text(text = "bedirhan@example.com", style = MaterialTheme.typography.bodyLarge)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            StatRow(icon = Icons.Default.Star, label = "Toplam Puan", value = "1250")
            StatRow(icon = Icons.Default.Star, label = "Yapılan İhbarlar", value = "12")
            StatRow(icon = Icons.Default.Star, label = "Geri Kazanılan Atık", value = "45kg")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedButton(
                onClick = { /* Ayarlar */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Profil Ayarları")
            }
        }
    }
}

@Composable
fun StatRow(icon: ImageVector, label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = label)
            }
            Text(text = value, style = MaterialTheme.typography.titleMedium)
        }
    }
}
