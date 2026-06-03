package com.example.mobil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Recommendation(val title: String, val description: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsScreen() {
    val recommendations = listOf(
        Recommendation("Kağıt Geri Dönüşümü", "Kağıtları geri dönüştürerek ağaçları koruyabilirsiniz."),
        Recommendation("Plastik Atıklar", "Plastik şişeleri durulamadan çöpe atmayın."),
        Recommendation("Cam Atıklar", "Cam sonsuz kez geri dönüştürülebilir bir malzemedir."),
        Recommendation("Elektronik Atıklar", "E-atıkları normal çöpe atmayın, özel toplama noktalarına bırakın."),
        Recommendation("Pil Atıkları", "Atık piller toprak ve su kirliliğine neden olur."),
        Recommendation("Gıda Atıkları", "Gıda atıklarından kompost gübre yaparak toprağı zenginleştirin.")
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Geri Dönüşüm Tavsiyeleri") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recommendations) { item ->
                RecommendationCard(item)
            }
        }
    }
}

@Composable
fun RecommendationCard(recommendation: Recommendation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = recommendation.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = recommendation.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
