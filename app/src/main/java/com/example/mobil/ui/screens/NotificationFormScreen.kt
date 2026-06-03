package com.example.mobil.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobil.util.NotificationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationFormScreen() {
    val context = LocalContext.current
    var boxId by remember { mutableStateOf("") }
    var wasteType by remember { mutableStateOf("") }
    var userNote by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                NotificationHelper.showNotification(
                    context,
                    "Başarılı",
                    "İhbarınız sisteme kaydedildi."
                )
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Atık Bildirim Formu") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Lütfen ihbar detaylarını doldurunuz.",
                style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = boxId,
                onValueChange = { boxId = it },
                label = { Text("Kutu ID") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = wasteType,
                onValueChange = { wasteType = it },
                label = { Text("Atık Tipi") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = userNote,
                onValueChange = { userNote = it },
                label = { Text("Kullanıcı Notu") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        NotificationHelper.showNotification(
                            context,
                            "Başarılı",
                            "İhbarınız sisteme kaydedildi."
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("İhbarı Gönder")
            }
        }
    }
}
