package com.safeguard.parental.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Welcome section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "¡Buen día!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Aquí tienes un resumen de la actividad de hoy",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }
        
        item {
            // Quick stats
            Text(
                text = "Estadísticas Rápidas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getQuickStats()) { stat ->
                    QuickStatCard(stat = stat)
                }
            }
        }
        
        item {
            // Active children section
            Text(
                text = "Niños Activos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(getActiveChildren()) { child ->
            ChildStatusCard(child = child)
        }
        
        item {
            // Recent alerts
            Text(
                text = "Alertas Recientes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(getRecentAlerts()) { alert ->
            AlertCard(alert = alert)
        }
        
        item {
            // Quick actions
            Text(
                text = "Acciones Rápidas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getQuickActions()) { action ->
                    QuickActionCard(
                        action = action,
                        onClick = { /* Handle action */ }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickStatCard(stat: QuickStat) {
    Card(
        modifier = Modifier.width(160.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = stat.icon,
                contentDescription = stat.title,
                tint = stat.color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stat.value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = stat.color
            )
            Text(
                text = stat.title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ChildStatusCard(child: ChildStatus) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar placeholder
            Surface(
                modifier = Modifier.size(48.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = child.name.first().toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = child.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Tiempo de pantalla: ${child.screenTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = child.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Status indicator
            Surface(
                shape = MaterialTheme.shapes.small,
                color = if (child.isOnline) Color(0xFF4CAF50) else Color(0xFFFF5722)
            ) {
                Text(
                    text = if (child.isOnline) "En línea" else "Fuera de línea",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun AlertCard(alert: Alert) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (alert.severity) {
                AlertSeverity.HIGH -> MaterialTheme.colorScheme.errorContainer
                AlertSeverity.MEDIUM -> MaterialTheme.colorScheme.secondaryContainer
                AlertSeverity.LOW -> MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = alert.icon,
                contentDescription = alert.title,
                tint = when (alert.severity) {
                    AlertSeverity.HIGH -> MaterialTheme.colorScheme.error
                    AlertSeverity.MEDIUM -> MaterialTheme.colorScheme.secondary
                    AlertSeverity.LOW -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = alert.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = alert.message,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = alert.time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    action: QuickAction,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.width(120.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = action.title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Data classes for demo data
data class QuickStat(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color
)

data class ChildStatus(
    val name: String,
    val screenTime: String,
    val location: String,
    val isOnline: Boolean
)

data class Alert(
    val title: String,
    val message: String,
    val time: String,
    val icon: ImageVector,
    val severity: AlertSeverity
)

enum class AlertSeverity { HIGH, MEDIUM, LOW }

data class QuickAction(
    val title: String,
    val icon: ImageVector
)

// Demo data functions
private fun getQuickStats(): List<QuickStat> = listOf(
    QuickStat("Niños Activos", "3", Icons.Default.People, Color(0xFF4CAF50)),
    QuickStat("Apps Bloqueadas", "12", Icons.Default.Block, Color(0xFFFF5722)),
    QuickStat("Tiempo Total", "6h 30m", Icons.Default.Schedule, Color(0xFF2196F3)),
    QuickStat("Alertas", "2", Icons.Default.Warning, Color(0xFFFF9800))
)

private fun getActiveChildren(): List<ChildStatus> = listOf(
    ChildStatus("Ana María", "2h 15m", "En casa", true),
    ChildStatus("Carlos", "1h 45m", "Escuela", true),
    ChildStatus("Sofia", "45m", "Parque", false)
)

private fun getRecentAlerts(): List<Alert> = listOf(
    Alert(
        "Límite de tiempo excedido",
        "Ana María ha excedido su límite diario en TikTok",
        "Hace 15 minutos",
        Icons.Default.Schedule,
        AlertSeverity.HIGH
    ),
    Alert(
        "Zona segura",
        "Carlos ha llegado a la escuela",
        "Hace 1 hora",
        Icons.Default.LocationOn,
        AlertSeverity.LOW
    )
)

private fun getQuickActions(): List<QuickAction> = listOf(
    QuickAction("Agregar Niño", Icons.Default.PersonAdd),
    QuickAction("Bloquear App", Icons.Default.Block),
    QuickAction("Ver Ubicación", Icons.Default.LocationOn),
    QuickAction("Generar Reporte", Icons.Default.Assessment)
)