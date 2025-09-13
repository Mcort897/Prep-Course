package com.parentguard.pro.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parentguard.pro.ui.theme.ParentGuardProTheme
import com.parentguard.pro.ui.viewmodel.MainViewModel
import com.parentguard.pro.utils.PermissionUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private lateinit var mainViewModel: MainViewModel
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            Log.d("MainActivity", "All permissions granted")
            mainViewModel.onPermissionsGranted()
        } else {
            Log.d("MainActivity", "Some permissions denied")
            mainViewModel.onPermissionsDenied()
        }
    }
    
    private val usageStatsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val hasPermission = Settings.System.canWrite(this)
            if (hasPermission) {
                mainViewModel.onUsageStatsPermissionGranted()
            } else {
                mainViewModel.onUsageStatsPermissionDenied()
            }
        }
    }
    
    private val accessibilityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val hasPermission = isAccessibilityServiceEnabled()
        if (hasPermission) {
            mainViewModel.onAccessibilityPermissionGranted()
        } else {
            mainViewModel.onAccessibilityPermissionDenied()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        mainViewModel = viewModel()
        
        setContent {
            ParentGuardProTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        viewModel = mainViewModel,
                        onRequestPermissions = { permissions ->
                            permissionLauncher.launch(permissions)
                        },
                        onOpenUsageStats = {
                            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                            usageStatsLauncher.launch(intent)
                        },
                        onOpenAccessibility = {
                            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            accessibilityLauncher.launch(intent)
                        }
                    )
                }
            }
        }
        
        // Check permissions on startup
        checkPermissions()
    }
    
    private fun checkPermissions() {
        val missingPermissions = PermissionUtils.getMissingCriticalPermissions(this)
        if (missingPermissions.isNotEmpty()) {
            mainViewModel.onPermissionsRequired(missingPermissions)
        } else {
            mainViewModel.onPermissionsGranted()
        }
    }
    
    private fun isAccessibilityServiceEnabled(): Boolean {
        val accessibilityEnabled = Settings.Secure.getInt(
            contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED,
            0
        )
        
        if (accessibilityEnabled == 1) {
            val services = Settings.Secure.getString(
                contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            return services?.contains(packageName) == true
        }
        return false
    }
    
    override fun onResume() {
        super.onResume()
        checkPermissions()
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onRequestPermissions: (Array<String>) -> Unit,
    onOpenUsageStats: () -> Unit,
    onOpenAccessibility: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
    
    when (uiState) {
        is MainUiState.Loading -> {
            LoadingScreen()
        }
        is MainUiState.PermissionsRequired -> {
            PermissionsScreen(
                missingPermissions = uiState.missingPermissions,
                onRequestPermissions = onRequestPermissions,
                onOpenUsageStats = onOpenUsageStats,
                onOpenAccessibility = onOpenAccessibility
            )
        }
        is MainUiState.AuthenticationRequired -> {
            AuthenticationScreen(
                onLogin = { email, password ->
                    viewModel.login(email, password)
                },
                onRegister = { email, password, userType ->
                    viewModel.register(email, password, userType)
                }
            )
        }
        is MainUiState.Dashboard -> {
            DashboardScreen(
                user = uiState.user,
                children = uiState.children,
                onNavigateToChildren = { viewModel.navigateToChildren() },
                onNavigateToReports = { viewModel.navigateToReports() },
                onNavigateToSettings = { viewModel.navigateToSettings() }
            )
        }
        is MainUiState.Error -> {
            ErrorScreen(
                message = uiState.message,
                onRetry = { viewModel.retry() }
            )
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cargando ParentGuard Pro...",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun PermissionsScreen(
    missingPermissions: List<String>,
    onRequestPermissions: (Array<String>) -> Unit,
    onOpenUsageStats: () -> Unit,
    onOpenAccessibility: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Permisos Requeridos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "ParentGuard Pro necesita varios permisos para funcionar correctamente. Por favor, otorga los permisos necesarios.",
            style = MaterialTheme.typography.bodyMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Critical permissions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Permisos Críticos",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                val criticalPermissions = missingPermissions.filter { permission ->
                    PermissionUtils.CRITICAL_PERMISSIONS.contains(permission)
                }
                
                criticalPermissions.forEach { permission ->
                    Text(
                        text = "• ${PermissionUtils.getPermissionTitle(permission)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        onRequestPermissions(criticalPermissions.toTypedArray())
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Otorgar Permisos Críticos")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Special permissions
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Permisos Especiales",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Estos permisos requieren configuración manual:",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = onOpenUsageStats,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Configurar Estadísticas de Uso")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = onOpenAccessibility,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Configurar Servicio de Accesibilidad")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                onRequestPermissions(missingPermissions.toTypedArray())
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Otorgar Todos los Permisos")
        }
    }
}

@Composable
fun AuthenticationScreen(
    onLogin: (String, String) -> Unit,
    onRegister: (String, String, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("parent") }
    var isLoginMode by remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isLoginMode) "Iniciar Sesión" else "Registrarse",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        
        if (!isLoginMode) {
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = userType == "parent",
                        onClick = { userType = "parent" }
                    )
                    Text("Padre/Madre")
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = userType == "child",
                        onClick = { userType = "child" }
                    )
                    Text("Hijo/Hija")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                if (isLoginMode) {
                    onLogin(email, password)
                } else {
                    if (password == confirmPassword) {
                        onRegister(email, password, userType)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoginMode) "Iniciar Sesión" else "Registrarse")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = { isLoginMode = !isLoginMode }
        ) {
            Text(
                if (isLoginMode) "¿No tienes cuenta? Regístrate" 
                else "¿Ya tienes cuenta? Inicia sesión"
            )
        }
    }
}

@Composable
fun DashboardScreen(
    user: com.parentguard.pro.data.model.User,
    children: List<com.parentguard.pro.data.model.Child>,
    onNavigateToChildren: () -> Unit,
    onNavigateToReports: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "¡Bienvenido, ${user.name}!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Tipo de usuario: ${if (user.userType == com.parentguard.pro.data.model.UserType.PARENT) "Padre/Madre" else "Hijo/Hija"}",
            style = MaterialTheme.typography.bodyMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        if (user.userType == com.parentguard.pro.data.model.UserType.PARENT) {
            // Parent dashboard
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Hijos Registrados: ${children.size}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = onNavigateToChildren,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Gestionar Hijos")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Button(
                        onClick = onNavigateToReports,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ver Reportes")
                    }
                }
            }
        } else {
            // Child dashboard
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Panel de Hijo",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Aquí puedes ver tu progreso y recompensas",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onNavigateToSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Configuración")
        }
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onRetry
        ) {
            Text("Reintentar")
        }
    }
}