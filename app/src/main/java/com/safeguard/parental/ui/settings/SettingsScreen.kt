package com.safeguard.parental.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(
    navController: NavController,
    onLogout: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Profile section
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(60.dp),
                        shape = MaterialTheme.shapes.large,
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = "Usuario Padre",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "padre@example.com",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Configuración General",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        items(getGeneralSettings()) { setting ->
            SettingsItem(
                setting = setting,
                onClick = { /* Handle click */ }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Monitoreo y Control",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        items(getMonitoringSettings()) { setting ->
            if (setting.isSwitch) {
                SwitchSettingsItem(
                    setting = setting,
                    onToggle = { /* Handle toggle */ }
                )
            } else {
                SettingsItem(
                    setting = setting,
                    onClick = { /* Handle click */ }
                )
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Notificaciones",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        items(getNotificationSettings()) { setting ->
            SwitchSettingsItem(
                setting = setting,
                onToggle = { /* Handle toggle */ }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cuenta y Datos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        items(getAccountSettings()) { setting ->
            SettingsItem(
                setting = setting,
                onClick = { 
                    if (setting.title == "Cerrar Sesión") {
                        showLogoutDialog = true
                    } else {
                        /* Handle other clicks */
                    }
                }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Información",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        items(getInfoSettings()) { setting ->
            SettingsItem(
                setting = setting,
                onClick = { /* Handle click */ }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
            // App version
            Text(
                text = "SafeGuard Parental v1.0.0",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    
    // Logout confirmation dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    }
                ) {
                    Text("Cerrar Sesión")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun SettingsItem(
    setting: SettingItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = setting.icon,
                contentDescription = setting.title,
                tint = if (setting.isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = setting.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = if (setting.isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
                if (setting.subtitle.isNotEmpty()) {
                    Text(
                        text = setting.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ir",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SwitchSettingsItem(
    setting: SettingItem,
    onToggle: (Boolean) -> Unit
) {
    var isEnabled by remember { mutableStateOf(setting.defaultValue) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = setting.icon,
                contentDescription = setting.title,
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = setting.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                if (setting.subtitle.isNotEmpty()) {
                    Text(
                        text = setting.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Switch(
                checked = isEnabled,
                onCheckedChange = { 
                    isEnabled = it
                    onToggle(it)
                }
            )
        }
    }
}

data class SettingItem(
    val title: String,
    val subtitle: String = "",
    val icon: ImageVector,
    val isSwitch: Boolean = false,
    val defaultValue: Boolean = false,
    val isDestructive: Boolean = false
)

private fun getGeneralSettings(): List<SettingItem> = listOf(
    SettingItem("Perfil de Usuario", "Editar información personal", Icons.Default.Person),
    SettingItem("Idioma", "Español", Icons.Default.Language),
    SettingItem("Tema", "Automático", Icons.Default.Palette),
    SettingItem("Zona Horaria", "America/Mexico_City", Icons.Default.Schedule)
)

private fun getMonitoringSettings(): List<SettingItem> = listOf(
    SettingItem("Monitoreo de Apps", "Supervisar uso de aplicaciones", Icons.Default.Apps, true, true),
    SettingItem("Seguimiento de Ubicación", "Rastrear ubicación GPS", Icons.Default.LocationOn, true, true),
    SettingItem("Filtros Web", "Bloquear contenido inapropiado", Icons.Default.Security, true, true),
    SettingItem("Administración del Dispositivo", "Configurar permisos avanzados", Icons.Default.AdminPanelSettings),
    SettingItem("Acceso a Estadísticas", "Configurar acceso a datos de uso", Icons.Default.BarChart)
)

private fun getNotificationSettings(): List<SettingItem> = listOf(
    SettingItem("Alertas de Tiempo", "Notificar límites de tiempo", Icons.Default.Schedule, true, true),
    SettingItem("Alertas de Ubicación", "Notificar cambios de ubicación", Icons.Default.LocationOn, true, true),
    SettingItem("Alertas de Apps", "Notificar uso de apps bloqueadas", Icons.Default.Block, true, true),
    SettingItem("Reportes Diarios", "Recibir resumen diario", Icons.Default.Assessment, true, true),
    SettingItem("Reportes Semanales", "Recibir resumen semanal", Icons.Default.DateRange, true, false)
)

private fun getAccountSettings(): List<SettingItem> = listOf(
    SettingItem("Cambiar Contraseña", "Actualizar contraseña de la cuenta", Icons.Default.Lock),
    SettingItem("Copia de Seguridad", "Respaldar datos en la nube", Icons.Default.CloudUpload),
    SettingItem("Exportar Datos", "Descargar todos los datos", Icons.Default.Download),
    SettingItem("Eliminar Cuenta", "Eliminar cuenta permanentemente", Icons.Default.DeleteForever, isDestructive = true),
    SettingItem("Cerrar Sesión", "Salir de la aplicación", Icons.Default.Logout, isDestructive = true)
)

private fun getInfoSettings(): List<SettingItem> = listOf(
    SettingItem("Ayuda y Soporte", "Centro de ayuda y contacto", Icons.Default.Help),
    SettingItem("Política de Privacidad", "Ver política de privacidad", Icons.Default.PrivacyTip),
    SettingItem("Términos de Servicio", "Ver términos y condiciones", Icons.Default.Description),
    SettingItem("Acerca de", "Información de la aplicación", Icons.Default.Info),
    SettingItem("Calificar App", "Calificar en Google Play", Icons.Default.Star)
)