package com.example.mobil.ui.screens

import android.Manifest
import android.os.Build
import android.widget.Toast
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobil.ui.viewmodel.NotificationViewModel
import com.example.mobil.util.NotificationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationFormScreen(viewModel: NotificationViewModel = viewModel()) {
    val context = LocalContext.current
    var boxId by remember { mutableStateOf("") }
    var wasteType by remember { mutableStateOf("") }
    var userNote by remember { mutableStateOf("") }

    val isSubmitting by viewModel.isSubmitting.collectAsState()
    val submitSuccess by viewModel.submitSuccess.collectAsState()

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

    LaunchedEffect(submitSuccess) {
        submitSuccess?.let { success ->
            if (success) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    NotificationHelper.showNotification(
                        context,
                        "Başarılı",
                        "İhbarınız sisteme kaydedildi."
                    )
                }
                // Formu temizle
                boxId = ""
                wasteType = ""
                userNote = ""
            } else {
                Toast.makeText(context, "Bir hata oluştu!", Toast.LENGTH_SHORT).show()
            }
            viewModel.resetStatus()
        }
    }

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
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting
            )

            OutlinedTextField(
                value = wasteType,
                onValueChange = { wasteType = it },
                label = { Text("Atık Tipi") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting
            )

            OutlinedTextField(
                value = userNote,
                onValueChange = { userNote = it },
                label = { Text("Kullanıcı Notu") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                enabled = !isSubmitting
            )

            Button(
                onClick = {
                    val id = boxId.toIntOrNull()
                    if (id != null) {
                        viewModel.sendIhbar(id, userNote)
                    } else {
                        Toast.makeText(context, "Geçerli bir Kutu ID giriniz!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("İhbarı Gönder")
                }
            }
        }
    }
}
