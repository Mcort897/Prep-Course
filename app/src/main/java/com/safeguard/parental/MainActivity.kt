package com.safeguard.parental

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.safeguard.parental.ui.auth.AuthScreen
import com.safeguard.parental.ui.auth.AuthViewModel
import com.safeguard.parental.ui.main.MainScreen
import com.safeguard.parental.ui.setup.SetupScreen
import com.safeguard.parental.ui.theme.SafeGuardParentalTheme
import com.safeguard.parental.utils.Screen

class MainActivity : ComponentActivity() {
    
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.Factory((application as SafeGuardApplication).authRepository)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            SafeGuardParentalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SafeGuardApp(authViewModel = authViewModel)
                }
            }
        }
    }
}

@Composable
fun SafeGuardApp(authViewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsState()
    val isFirstLaunch by authViewModel.isFirstLaunch.collectAsState()
    
    NavHost(
        navController = navController,
        startDestination = when {
            isFirstLaunch -> Screen.Setup.route
            authState.isAuthenticated -> Screen.Main.route
            else -> Screen.Auth.route
        }
    ) {
        composable(Screen.Setup.route) {
            SetupScreen(
                onSetupComplete = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Setup.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Auth.route) {
            AuthScreen(
                viewModel = authViewModel,
                onAuthSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Main.route) {
            MainScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            )
        }
    }
}