package com.safeguard.parental.ui.location

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LocationScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Ubicaciones", "Zonas Seguras", "Historial")
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Tab row
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Content based on selected tab
        when (selectedTab) {
            0 -> CurrentLocationsContent()
            1 -> SafeZonesContent()
            2 -> LocationHistoryContent()
        }
    }
}

@Composable
private fun CurrentLocationsContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ubicaciones Actuales",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = { /* Refresh locations */ }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar")
                }
            }
        }
        
        items(getDemoCurrentLocations()) { location ->
            CurrentLocationCard(location = location)
        }
    }
}

@Composable
private fun SafeZonesContent() {
    var showAddZoneDialog by remember { mutableStateOf(false) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Zonas Seguras",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                FloatingActionButton(
                    onClick = { showAddZoneDialog = true },
                    modifier = Modifier.size(48.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar zona")
                }
            }
        }
        
        items(getDemoSafeZones()) { zone ->
            SafeZoneCard(
                zone = zone,
                onEditClick = { /* Handle edit */ },
                onDeleteClick = { /* Handle delete */ }
            )
        }
    }
    
    if (showAddZoneDialog) {
        AddSafeZoneDialog(
            onDismiss = { showAddZoneDialog = false },
            onConfirm = { name, address, radius ->
                // Handle add zone
                showAddZoneDialog = false
            }
        )
    }
}

@Composable
private fun LocationHistoryContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Historial de Ubicaciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        items(getDemoLocationHistory()) { historyItem ->
            LocationHistoryCard(historyItem = historyItem)
        }
    }
}

@Composable
private fun CurrentLocationCard(location: CurrentLocation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Child avatar
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = location.childName.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = location.childName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = location.address,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Actualizado: ${location.lastUpdate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Status and actions
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = if (location.isInSafeZone) Color(0xFF4CAF50) else Color(0xFFFF9800)
                    ) {
                        Text(
                            text = if (location.isInSafeZone) "Zona Segura" else "Fuera de zona",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    IconButton(onClick = { /* Open in maps */ }) {
                        Icon(
                            Icons.Default.Map,
                            contentDescription = "Ver en mapa",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            if (location.batteryLevel < 20) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.BatteryAlert,
                        contentDescription = "Batería baja",
                        tint = Color(0xFFFF5722),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Batería baja: ${location.batteryLevel}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFFF5722)
                    )
                }
            }
        }
    }
}

@Composable
private fun SafeZoneCard(
    zone: SafeZoneInfo,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = zone.name,
                tint = Color(zone.color),
                modifier = Modifier.size(40.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = zone.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = zone.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Radio: ${zone.radius}m",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationHistoryCard(historyItem: LocationHistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = "Historial",
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = historyItem.childName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = historyItem.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = historyItem.time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AddSafeZoneDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var radius by remember { mutableStateOf("100") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar Zona Segura") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre de la zona") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = radius,
                    onValueChange = { radius = it },
                    label = { Text("Radio (metros)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && address.isNotBlank() && radius.isNotBlank()) {
                        onConfirm(name, address, radius.toIntOrNull() ?: 100)
                    }
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

// Data classes
data class CurrentLocation(
    val childName: String,
    val address: String,
    val lastUpdate: String,
    val isInSafeZone: Boolean,
    val batteryLevel: Int
)

data class SafeZoneInfo(
    val name: String,
    val address: String,
    val radius: Int,
    val color: Long
)

data class LocationHistoryItem(
    val childName: String,
    val address: String,
    val time: String
)

// Demo data
private fun getDemoCurrentLocations(): List<CurrentLocation> = listOf(
    CurrentLocation(
        childName = "Ana María",
        address = "Calle Principal 123, Centro",
        lastUpdate = "Hace 2 minutos",
        isInSafeZone = true,
        batteryLevel = 85
    ),
    CurrentLocation(
        childName = "Carlos",
        address = "Escuela Primaria San José",
        lastUpdate = "Hace 5 minutos",
        isInSafeZone = true,
        batteryLevel = 45
    ),
    CurrentLocation(
        childName = "Sofia",
        address = "Parque Central",
        lastUpdate = "Hace 15 minutos",
        isInSafeZone = false,
        batteryLevel = 15
    )
)

private fun getDemoSafeZones(): List<SafeZoneInfo> = listOf(
    SafeZoneInfo(
        name = "Casa",
        address = "Calle Principal 123",
        radius = 100,
        color = 0xFF4CAF50
    ),
    SafeZoneInfo(
        name = "Escuela",
        address = "Escuela Primaria San José",
        radius = 150,
        color = 0xFF2196F3
    ),
    SafeZoneInfo(
        name = "Casa de los Abuelos",
        address = "Avenida Central 456",
        radius = 80,
        color = 0xFFFF9800
    )
)

private fun getDemoLocationHistory(): List<LocationHistoryItem> = listOf(
    LocationHistoryItem("Ana María", "Casa", "14:30"),
    LocationHistoryItem("Carlos", "Escuela", "14:15"),
    LocationHistoryItem("Sofia", "Parque Central", "14:00"),
    LocationHistoryItem("Ana María", "Centro Comercial", "13:45"),
    LocationHistoryItem("Carlos", "Casa", "13:30")
)