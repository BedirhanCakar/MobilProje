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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobil.ui.viewmodel.HomeViewModel
import com.example.mobil.ui.viewmodel.NotificationViewModel
import com.example.mobil.util.NotificationHelper
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationFormScreen(
    locationInfo: String = "",
    onNavigateBack: () -> Unit = {},
    notificationViewModel: NotificationViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    var wasteType by remember { mutableStateOf("") }
    var userNote by remember { mutableStateOf("") }

    val isSubmitting by notificationViewModel.isSubmitting.collectAsState()
    val submitSuccess by notificationViewModel.submitSuccess.collectAsState()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                NotificationHelper.showNotification(
                    context,
                    "Mobil Atık Takip",
                    "Yeni bir atık var, hadi hemen çevreyi koruyalım!"
                )
            }
        }
    )

    LaunchedEffect(submitSuccess) {
        submitSuccess?.let { success ->
            if (success) {
                Toast.makeText(context, "Bildirim başarıyla gönderildi!", Toast.LENGTH_SHORT).show()
                
                // Haritaya yeni işaretçiyi ekle
                if (locationInfo.isNotEmpty()) {
                    val coords = locationInfo.split(",")
                    if (coords.size == 2) {
                        val lat = coords[0].trim().toDoubleOrNull()
                        val lng = coords[1].trim().toDoubleOrNull()
                        if (lat != null && lng != null) {
                            homeViewModel.addMarker(LatLng(lat, lng), wasteType)
                        }
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    NotificationHelper.showNotification(
                        context,
                        "Mobil Atık Takip",
                        "Yeni bir atık var, hadi hemen çevreyi koruyalım!"
                    )
                }
                
                onNavigateBack()
            } else {
                Toast.makeText(context, "Bir hata oluştu!", Toast.LENGTH_SHORT).show()
            }
            notificationViewModel.resetStatus()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Atık Bildirimi Gönder") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            ElevatedCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "İhbar Detayları",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = locationInfo,
                        onValueChange = { },
                        label = { Text("Seçilen Konum") },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        readOnly = true
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
                            if (locationInfo.isNotEmpty() && wasteType.isNotEmpty()) {
                                notificationViewModel.sendIhbar(1, userNote)
                            } else {
                                Toast.makeText(context, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isSubmitting,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Bildirimi Gönder")
                        }
                    }
                }
            }
        }
    }
}
