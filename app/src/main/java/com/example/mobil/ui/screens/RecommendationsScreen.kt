package com.example.mobil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class Recommendation(val title: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen() {
    val recommendations = listOf(
        Recommendation("Kağıt Geri Dönüşümü", "Kağıtları geri dönüştürerek ağaçları koruyabilirsiniz."),
        Recommendation("Plastik Atıklar", "Plastik şişeleri durulamadan çöpe atmayın."),
        Recommendation("Cam Atıklar", "Cam sonsuz kez geri dönüştürülebilir bir malzemedir."),
        Recommendation("Elektronik Atıklar", "E-atıkları özel toplama noktalarına bırakın."),
        Recommendation("Pil Atıkları", "Atık piller toprak ve su kirliliğine neden olur."),
        Recommendation("Gıda Atıkları", "Gıda atıklarından kompost gübre yaparak toprağı zenginleştirin.")
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tavsiyeler") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Daha Yeşil Bir Dünya İçin",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(recommendations) { item ->
                RecommendationCard(item)
            }
        }
    }
}

@Composable
fun RecommendationCard(recommendation: Recommendation) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = recommendation.title, 
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = recommendation.description, 
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
