package com.example.mobil.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobil.ui.viewmodel.HomeViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToNotification: (Double, Double) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val kutular by viewModel.kutular.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    var hasLocationPermission by remember { mutableStateOf(false) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(41.0082, 28.9784), 10f)
    }

    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
                }
            }
        }
    }

    if (showConfirmDialog && selectedLocation != null) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Konum Onayı") },
            text = { Text("Bu konumu işaretlemek istediğinizden emin misiniz?") },
            confirmButton = {
                Button(onClick = {
                    showConfirmDialog = false
                    onNavigateToNotification(selectedLocation!!.latitude, selectedLocation!!.longitude)
                }) { Text("Evet") }
            },
            dismissButton = {
                TextButton(onClick = { 
                    showConfirmDialog = false 
                    selectedLocation = null
                }) { Text("Hayır") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Harita Üzerinden Seçim Yapın") },
                actions = {
                    IconButton(onClick = { viewModel.fetchKutular() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Yenile")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
                uiSettings = MapUiSettings(myLocationButtonEnabled = true),
                onMapClick = { latLng ->
                    selectedLocation = latLng
                    showConfirmDialog = true
                }
            ) {
                // Mevcut Kutular
                kutular.forEach { kutu ->
                    Marker(
                        state = rememberMarkerState(position = LatLng(kutu.lat, kutu.lng)),
                        title = "${kutu.tip} Kutusu",
                        snippet = "Doluluk: %${kutu.dolulukOrani}"
                    )
                }

                // Seçilen Konum (Kırmızı Nokta/Marker)
                selectedLocation?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Seçilen Konum",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                }
            }
            
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
