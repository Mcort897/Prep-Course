package com.safeguard.parental.ui.setup

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.safeguard.parental.R
import com.safeguard.parental.services.DeviceAdminReceiver
import com.safeguard.parental.utils.Constants

@Composable
fun SetupScreen(
    onSetupComplete: () -> Unit
) {
    val context = LocalContext.current
    var currentStep by remember { mutableStateOf(0) }
    var deviceAdminEnabled by remember { mutableStateOf(false) }
    var usageAccessEnabled by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(false) }
    
    val steps = listOf(
        "Bienvenida",
        "Administración del Dispositivo",
        "Acceso a Estadísticas de Uso",
        "Permisos de Ubicación",
        "Configuración Completa"
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress indicator
        LinearProgressIndicator(
            progress = (currentStep + 1) / steps.size.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        
        Text(
            text = "Paso ${currentStep + 1} de ${steps.size}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Text(
            text = steps[currentStep],
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        when (currentStep) {
            0 -> WelcomeStep()
            1 -> DeviceAdminStep(
                isEnabled = deviceAdminEnabled,
                onEnableClick = {
                    enableDeviceAdmin(context)
                    deviceAdminEnabled = true
                }
            )
            2 -> UsageAccessStep(
                isEnabled = usageAccessEnabled,
                onEnableClick = {
                    enableUsageAccess(context)
                    usageAccessEnabled = true
                }
            )
            3 -> LocationPermissionStep(
                isEnabled = locationEnabled,
                onEnableClick = {
                    // Location permission would be handled in the main app
                    locationEnabled = true
                }
            )
            4 -> SetupCompleteStep()
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStep > 0) {
                OutlinedButton(
                    onClick = { currentStep-- }
                ) {
                    Text("Anterior")
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }
            
            Button(
                onClick = {
                    if (currentStep < steps.size - 1) {
                        currentStep++
                    } else {
                        // Mark first launch as complete
                        val prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
                        prefs.edit().putBoolean(Constants.PREF_FIRST_LAUNCH, false).apply()
                        onSetupComplete()
                    }
                }
            ) {
                Text(if (currentStep < steps.size - 1) "Siguiente" else "Finalizar")
            }
        }
    }
}

@Composable
private fun WelcomeStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "SafeGuard Logo",
            modifier = Modifier.size(120.dp)
        )
        
        Text(
            text = "¡Bienvenido a SafeGuard!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = "La aplicación de control parental más avanzada y segura. Te ayudaremos a configurar todos los permisos necesarios para proteger a tus hijos.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Características principales:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                FeatureItem("📱", "Monitoreo de aplicaciones en tiempo real")
                FeatureItem("📍", "Ubicación GPS con zonas seguras")
                FeatureItem("⏰", "Control de tiempo de pantalla")
                FeatureItem("🛡️", "Filtros de contenido web avanzados")
                FeatureItem("📊", "Reportes detallados de actividad")
                FeatureItem("🔔", "Alertas inteligentes")
            }
        }
    }
}

@Composable
private fun FeatureItem(icon: String, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun DeviceAdminStep(
    isEnabled: Boolean,
    onEnableClick: () -> Unit
) {
    SetupStepCard(
        icon = Icons.Default.Security,
        title = "Administración del Dispositivo",
        description = "Para poder bloquear aplicaciones y controlar el dispositivo, necesitamos permisos de administrador.",
        isEnabled = isEnabled,
        onEnableClick = onEnableClick,
        buttonText = "Habilitar Administración"
    )
}

@Composable
private fun UsageAccessStep(
    isEnabled: Boolean,
    onEnableClick: () -> Unit
) {
    SetupStepCard(
        icon = Icons.Default.Apps,
        title = "Acceso a Estadísticas de Uso",
        description = "Para monitorear el tiempo de uso de las aplicaciones, necesitamos acceso a las estadísticas del sistema.",
        isEnabled = isEnabled,
        onEnableClick = onEnableClick,
        buttonText = "Habilitar Acceso"
    )
}

@Composable
private fun LocationPermissionStep(
    isEnabled: Boolean,
    onEnableClick: () -> Unit
) {
    SetupStepCard(
        icon = Icons.Default.LocationOn,
        title = "Permisos de Ubicación",
        description = "Para rastrear la ubicación y crear zonas seguras, necesitamos acceso a la ubicación del dispositivo.",
        isEnabled = isEnabled,
        onEnableClick = onEnableClick,
        buttonText = "Permitir Ubicación"
    )
}

@Composable
private fun SetupCompleteStep() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Completado",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(80.dp)
        )
        
        Text(
            text = "¡Configuración Completada!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Text(
            text = "SafeGuard está listo para proteger a tu familia. Ahora puedes crear tu cuenta y comenzar a agregar a tus hijos.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SetupStepCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    isEnabled: Boolean,
    onEnableClick: () -> Unit,
    buttonText: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(64.dp)
            )
            
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            if (isEnabled) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Habilitado",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Configurado correctamente",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                Button(
                    onClick = onEnableClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(buttonText)
                }
            }
        }
    }
}

private fun enableDeviceAdmin(context: Context) {
    val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    val componentName = ComponentName(context, DeviceAdminReceiver::class.java)
    
    if (!devicePolicyManager.isAdminActive(componentName)) {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
            putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, 
                "SafeGuard necesita permisos de administrador para funcionar correctamente")
        }
        context.startActivity(intent)
    }
}

private fun enableUsageAccess(context: Context) {
    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    context.startActivity(intent)
}