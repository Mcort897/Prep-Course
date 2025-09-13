package com.safeguard.parental.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.safeguard.parental.ui.dashboard.DashboardScreen
import com.safeguard.parental.ui.children.ChildrenScreen
import com.safeguard.parental.ui.apps.AppsScreen
import com.safeguard.parental.ui.location.LocationScreen
import com.safeguard.parental.ui.reports.ReportsScreen
import com.safeguard.parental.ui.settings.SettingsScreen
import com.safeguard.parental.utils.Screen

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomNavItems = listOf(
        BottomNavItem(Screen.Dashboard.route, Icons.Default.Dashboard, "Dashboard"),
        BottomNavItem(Screen.Children.route, Icons.Default.People, "Niños"),
        BottomNavItem(Screen.Apps.route, Icons.Default.Apps, "Apps"),
        BottomNavItem(Screen.Location.route, Icons.Default.LocationOn, "Ubicación"),
        BottomNavItem(Screen.Reports.route, Icons.Default.Assessment, "Reportes")
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = when (currentDestination?.route) {
                            Screen.Dashboard.route -> "Dashboard"
                            Screen.Children.route -> "Niños"
                            Screen.Apps.route -> "Aplicaciones"
                            Screen.Location.route -> "Ubicación"
                            Screen.Reports.route -> "Reportes"
                            Screen.Settings.route -> "Configuración"
                            else -> "SafeGuard"
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Settings.route)
                        }
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Configuración")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(navController = navController)
            }
            composable(Screen.Children.route) {
                ChildrenScreen(navController = navController)
            }
            composable(Screen.Apps.route) {
                AppsScreen(navController = navController)
            }
            composable(Screen.Location.route) {
                LocationScreen(navController = navController)
            }
            composable(Screen.Reports.route) {
                ReportsScreen(navController = navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    navController = navController,
                    onLogout = onLogout
                )
            }
        }
    }
}