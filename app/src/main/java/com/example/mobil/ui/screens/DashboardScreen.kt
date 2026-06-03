package com.example.mobil.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobil.data.model.Kutu
import com.example.mobil.ui.viewmodel.HomeViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: HomeViewModel = viewModel()) {
    val context = LocalContext.current
    val kutular by viewModel.kutular.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    var hasLocationPermission by remember { mutableStateOf(false) }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(41.0082, 28.9784), 10f)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }
    )

    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        LatLng(it.latitude, it.longitude),
                        15f
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mobil Atık Takip") },
                actions = {
                    IconButton(onClick = { viewModel.fetchKutular() }) {
                        Icon(Icons.Default.Search, contentDescription = "Yenile")
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
                    uiSettings = MapUiSettings(myLocationButtonEnabled = true)
                ) {
                    kutular.forEach { kutu ->
                        Marker(
                            state = rememberMarkerState(position = LatLng(kutu.lat, kutu.lng)),
                            title = "${kutu.tip} Kutusu",
                            snippet = "Doluluk: %${kutu.dolulukOrani}"
                        )
                    }
                }
                
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Yakındaki Kutular",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(kutular) { kutu ->
                    KutuCard(kutu)
                }
            }
        }
    }
}

@Composable
fun KutuCard(kutu: Kutu) {
    Card(
        modifier = Modifier.width(150.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = kutu.tip, style = MaterialTheme.typography.titleMedium)
            Text(text = "%${kutu.dolulukOrani} Dolu", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { kutu.dolulukOrani / 100f },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
