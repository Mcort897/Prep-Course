package com.safeguard.parental.ui.reports

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
fun ReportsScreen(navController: NavController) {
    var selectedPeriod by remember { mutableStateOf("Hoy") }
    val periods = listOf("Hoy", "Esta semana", "Este mes", "Personalizado")
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Period selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                periods.forEach { period ->
                    FilterChip(
                        onClick = { selectedPeriod = period },
                        label = { Text(period) },
                        selected = selectedPeriod == period
                    )
                }
            }
        }
        
        item {
            // Summary cards
            Text(
                text = "Resumen - $selectedPeriod",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    title = "Tiempo Total",
                    value = "8h 45m",
                    icon = Icons.Default.Schedule,
                    color = Color(0xFF2196F3),
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Apps Más Usadas",
                    value = "5",
                    icon = Icons.Default.Apps,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(
                    title = "Alertas",
                    value = "3",
                    icon = Icons.Default.Warning,
                    color = Color(0xFFFF9800),
                    modifier = Modifier.weight(1f)
                )
                SummaryCard(
                    title = "Ubicaciones",
                    value = "12",
                    icon = Icons.Default.LocationOn,
                    color = Color(0xFF9C27B0),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        item {
            // Screen time by child
            Text(
                text = "Tiempo de Pantalla por Niño",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(getScreenTimeByChild()) { childReport ->
            ChildScreenTimeCard(childReport = childReport)
        }
        
        item {
            // Most used apps
            Text(
                text = "Aplicaciones Más Utilizadas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(getMostUsedApps()) { appReport ->
            AppUsageCard(appReport = appReport)
        }
        
        item {
            // Location summary
            Text(
                text = "Resumen de Ubicaciones",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(getLocationSummary()) { locationReport ->
            LocationSummaryCard(locationReport = locationReport)
        }
        
        item {
            // Export options
            Text(
                text = "Exportar Reportes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { /* Export PDF */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.PictureAsPdf, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("PDF")
                }
                
                OutlinedButton(
                    onClick = { /* Export Excel */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.TableChart, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Excel")
                }
                
                OutlinedButton(
                    onClick = { /* Share report */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Compartir")
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ChildScreenTimeCard(childReport: ChildScreenTimeReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = childReport.name.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = childReport.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tiempo total: ${childReport.totalTime}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = childReport.averageDaily,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Progress bar showing usage vs limit
            LinearProgressIndicator(
                progress = childReport.usageProgress,
                modifier = Modifier.fillMaxWidth(),
                color = if (childReport.usageProgress > 0.8f) Color(0xFFFF5722) else MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "0h",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Límite: ${childReport.dailyLimit}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AppUsageCard(appReport: AppUsageReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = appReport.name,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = appReport.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Categoría: ${appReport.category}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = appReport.totalTime,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${appReport.sessions} sesiones",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun LocationSummaryCard(locationReport: LocationSummaryReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = locationReport.childName,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = locationReport.childName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tiempo en zonas seguras: ${locationReport.timeInSafeZones}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Lugares visitados: ${locationReport.placesVisited}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Surface(
                shape = MaterialTheme.shapes.small,
                color = if (locationReport.safeZoneCompliance > 0.8f) Color(0xFF4CAF50) else Color(0xFFFF9800)
            ) {
                Text(
                    text = "${(locationReport.safeZoneCompliance * 100).toInt()}%",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    }
}

// Data classes
data class ChildScreenTimeReport(
    val name: String,
    val totalTime: String,
    val averageDaily: String,
    val dailyLimit: String,
    val usageProgress: Float
)

data class AppUsageReport(
    val name: String,
    val category: String,
    val totalTime: String,
    val sessions: Int
)

data class LocationSummaryReport(
    val childName: String,
    val timeInSafeZones: String,
    val placesVisited: Int,
    val safeZoneCompliance: Float
)

// Demo data
private fun getScreenTimeByChild(): List<ChildScreenTimeReport> = listOf(
    ChildScreenTimeReport(
        name = "Ana María",
        totalTime = "15h 30m",
        averageDaily = "2h 30m",
        dailyLimit = "3h",
        usageProgress = 0.83f
    ),
    ChildScreenTimeReport(
        name = "Carlos",
        totalTime = "8h 15m",
        averageDaily = "1h 20m",
        dailyLimit = "2h",
        usageProgress = 0.67f
    ),
    ChildScreenTimeReport(
        name = "Sofia",
        totalTime = "12h 45m",
        averageDaily = "2h 5m",
        dailyLimit = "2h 30m",
        usageProgress = 0.83f
    )
)

private fun getMostUsedApps(): List<AppUsageReport> = listOf(
    AppUsageReport("TikTok", "Social Media", "4h 30m", 25),
    AppUsageReport("YouTube", "Video", "3h 15m", 18),
    AppUsageReport("WhatsApp", "Communication", "2h 45m", 35),
    AppUsageReport("Instagram", "Social Media", "2h 20m", 15),
    AppUsageReport("Minecraft", "Games", "1h 50m", 8)
)

private fun getLocationSummary(): List<LocationSummaryReport> = listOf(
    LocationSummaryReport(
        childName = "Ana María",
        timeInSafeZones = "85%",
        placesVisited = 8,
        safeZoneCompliance = 0.85f
    ),
    LocationSummaryReport(
        childName = "Carlos",
        timeInSafeZones = "92%",
        placesVisited = 5,
        safeZoneCompliance = 0.92f
    ),
    LocationSummaryReport(
        childName = "Sofia",
        timeInSafeZones = "73%",
        placesVisited = 12,
        safeZoneCompliance = 0.73f
    )
)