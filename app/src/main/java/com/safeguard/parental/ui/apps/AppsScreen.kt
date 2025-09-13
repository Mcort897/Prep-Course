package com.safeguard.parental.ui.apps

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
fun AppsScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Todas", "Bloqueadas", "Permitidas", "Límites")
    
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
            0 -> AllAppsContent()
            1 -> BlockedAppsContent()
            2 -> AllowedAppsContent()
            3 -> AppLimitsContent()
        }
    }
}

@Composable
private fun AllAppsContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Aplicaciones Instaladas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        items(getDemoApps()) { app ->
            AppCard(
                app = app,
                onBlockClick = { /* Handle block */ },
                onAllowClick = { /* Handle allow */ },
                onSetLimitClick = { /* Handle set limit */ }
            )
        }
    }
}

@Composable
private fun BlockedAppsContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Aplicaciones Bloqueadas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        items(getDemoApps().filter { it.isBlocked }) { app ->
            AppCard(
                app = app,
                onBlockClick = { /* Handle unblock */ },
                onAllowClick = { /* Handle allow */ },
                onSetLimitClick = { /* Handle set limit */ }
            )
        }
    }
}

@Composable
private fun AllowedAppsContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Aplicaciones Permitidas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        items(getDemoApps().filter { !it.isBlocked }) { app ->
            AppCard(
                app = app,
                onBlockClick = { /* Handle block */ },
                onAllowClick = { /* Handle allow */ },
                onSetLimitClick = { /* Handle set limit */ }
            )
        }
    }
}

@Composable
private fun AppLimitsContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Límites de Tiempo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        items(getDemoApps().filter { it.hasTimeLimit }) { app ->
            AppLimitCard(app = app)
        }
    }
}

@Composable
private fun AppCard(
    app: AppInfo,
    onBlockClick: () -> Unit,
    onAllowClick: () -> Unit,
    onSetLimitClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App icon placeholder
            Surface(
                modifier = Modifier.size(48.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Android,
                        contentDescription = app.name,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = app.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = app.packageName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Categoría: ${app.category}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (app.todayUsage.isNotEmpty()) {
                    Text(
                        text = "Uso hoy: ${app.todayUsage}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Status chip
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = if (app.isBlocked) Color(0xFFFF5722) else Color(0xFF4CAF50)
                ) {
                    Text(
                        text = if (app.isBlocked) "Bloqueada" else "Permitida",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
                
                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = if (app.isBlocked) onAllowClick else onBlockClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (app.isBlocked) Icons.Default.Check else Icons.Default.Block,
                            contentDescription = if (app.isBlocked) "Permitir" else "Bloquear",
                            tint = if (app.isBlocked) Color(0xFF4CAF50) else Color(0xFFFF5722),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    IconButton(
                        onClick = onSetLimitClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Establecer límite",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AppLimitCard(app: AppInfo) {
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
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = app.name,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = app.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Límite: ${app.dailyLimit}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = app.todayUsage,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Progress bar
            LinearProgressIndicator(
                progress = app.usageProgress,
                modifier = Modifier.fillMaxWidth(),
                color = if (app.usageProgress > 0.8f) Color(0xFFFF5722) else MaterialTheme.colorScheme.primary
            )
        }
    }
}

data class AppInfo(
    val name: String,
    val packageName: String,
    val category: String,
    val isBlocked: Boolean,
    val hasTimeLimit: Boolean,
    val dailyLimit: String,
    val todayUsage: String,
    val usageProgress: Float
)

private fun getDemoApps(): List<AppInfo> = listOf(
    AppInfo(
        name = "TikTok",
        packageName = "com.zhiliaoapp.musically",
        category = "Social Media",
        isBlocked = false,
        hasTimeLimit = true,
        dailyLimit = "1h 30m",
        todayUsage = "2h 15m",
        usageProgress = 1.5f
    ),
    AppInfo(
        name = "Instagram",
        packageName = "com.instagram.android",
        category = "Social Media",
        isBlocked = true,
        hasTimeLimit = false,
        dailyLimit = "",
        todayUsage = "0m",
        usageProgress = 0f
    ),
    AppInfo(
        name = "YouTube",
        packageName = "com.google.android.youtube",
        category = "Video",
        isBlocked = false,
        hasTimeLimit = true,
        dailyLimit = "2h",
        todayUsage = "45m",
        usageProgress = 0.375f
    ),
    AppInfo(
        name = "WhatsApp",
        packageName = "com.whatsapp",
        category = "Communication",
        isBlocked = false,
        hasTimeLimit = false,
        dailyLimit = "",
        todayUsage = "1h 20m",
        usageProgress = 0f
    ),
    AppInfo(
        name = "Minecraft",
        packageName = "com.mojang.minecraftpe",
        category = "Games",
        isBlocked = false,
        hasTimeLimit = true,
        dailyLimit = "1h",
        todayUsage = "30m",
        usageProgress = 0.5f
    )
)