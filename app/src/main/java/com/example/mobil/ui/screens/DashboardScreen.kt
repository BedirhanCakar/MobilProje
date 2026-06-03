package com.example.mobil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mobil Atık Takip") },
                actions = {
                    IconButton(onClick = { /* Ara */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Ara")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Harita Alanı (Step 4'te eklenecek)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("Harita Alanı (Step 4'te Gelecek)")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Yakındaki Kutular",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val dummyBoxes = listOf("Plastik", "Kağıt", "Cam", "Metal")

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dummyBoxes) { type ->
                    KutuCard(type)
                }
            }
        }
    }
}

@Composable
fun KutuCard(type: String) {
    Card(
        modifier = Modifier.width(150.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = type, style = MaterialTheme.typography.titleMedium)
            Text(text = "%60 Dolu", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.6f },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
