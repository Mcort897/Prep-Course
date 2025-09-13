package com.parentguard.pro.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parentguard.pro.data.model.User
import com.parentguard.pro.data.model.Child
import com.parentguard.pro.data.model.UserType
import com.parentguard.pro.data.repository.UserRepository
import com.parentguard.pro.data.repository.ChildRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val childRepository: ChildRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _children = MutableStateFlow<List<Child>>(emptyList())
    val children: StateFlow<List<Child>> = _children.asStateFlow()
    
    fun initialize() {
        viewModelScope.launch {
            try {
                // Check if user is already logged in
                val user = getCurrentUser()
                if (user != null) {
                    _currentUser.value = user
                    if (user.userType == UserType.PARENT) {
                        loadChildren(user.id)
                    }
                    _uiState.value = MainUiState.Dashboard(user, _children.value)
                } else {
                    _uiState.value = MainUiState.AuthenticationRequired
                }
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error("Error al inicializar la aplicación: ${e.message}")
            }
        }
    }
    
    fun onPermissionsRequired(missingPermissions: List<String>) {
        _uiState.value = MainUiState.PermissionsRequired(missingPermissions)
    }
    
    fun onPermissionsGranted() {
        // Check if user is logged in
        viewModelScope.launch {
            val user = getCurrentUser()
            if (user != null) {
                _currentUser.value = user
                if (user.userType == UserType.PARENT) {
                    loadChildren(user.id)
                }
                _uiState.value = MainUiState.Dashboard(user, _children.value)
            } else {
                _uiState.value = MainUiState.AuthenticationRequired
            }
        }
    }
    
    fun onPermissionsDenied() {
        _uiState.value = MainUiState.Error("Se requieren permisos para usar la aplicación")
    }
    
    fun onUsageStatsPermissionGranted() {
        // Handle usage stats permission granted
    }
    
    fun onUsageStatsPermissionDenied() {
        // Handle usage stats permission denied
    }
    
    fun onAccessibilityPermissionGranted() {
        // Handle accessibility permission granted
    }
    
    fun onAccessibilityPermissionDenied() {
        // Handle accessibility permission denied
    }
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                _uiState.value = MainUiState.Loading
                
                // TODO: Implement Firebase authentication
                val user = User(
                    id = "user_${System.currentTimeMillis()}",
                    email = email,
                    name = "Usuario",
                    userType = UserType.PARENT, // Default to parent for now
                    familyCode = "FAMILY_${System.currentTimeMillis()}",
                    isActive = true
                )
                
                userRepository.insertUser(user)
                _currentUser.value = user
                
                if (user.userType == UserType.PARENT) {
                    loadChildren(user.id)
                }
                
                _uiState.value = MainUiState.Dashboard(user, _children.value)
                
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error("Error al iniciar sesión: ${e.message}")
            }
        }
    }
    
    fun register(email: String, password: String, userType: String) {
        viewModelScope.launch {
            try {
                _uiState.value = MainUiState.Loading
                
                // TODO: Implement Firebase authentication
                val user = User(
                    id = "user_${System.currentTimeMillis()}",
                    email = email,
                    name = "Usuario",
                    userType = if (userType == "parent") UserType.PARENT else UserType.CHILD,
                    familyCode = "FAMILY_${System.currentTimeMillis()}",
                    isActive = true
                )
                
                userRepository.insertUser(user)
                _currentUser.value = user
                
                if (user.userType == UserType.PARENT) {
                    loadChildren(user.id)
                }
                
                _uiState.value = MainUiState.Dashboard(user, _children.value)
                
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error("Error al registrarse: ${e.message}")
            }
        }
    }
    
    fun navigateToChildren() {
        // TODO: Navigate to children management screen
    }
    
    fun navigateToReports() {
        // TODO: Navigate to reports screen
    }
    
    fun navigateToSettings() {
        // TODO: Navigate to settings screen
    }
    
    fun retry() {
        initialize()
    }
    
    private suspend fun getCurrentUser(): User? {
        // TODO: Implement getting current user from Firebase Auth
        return null
    }
    
    private suspend fun loadChildren(parentId: String) {
        try {
            val children = childRepository.getChildrenByParentId(parentId)
            _children.value = children
        } catch (e: Exception) {
            // Handle error
        }
    }
}

sealed class MainUiState {
    object Loading : MainUiState()
    data class PermissionsRequired(val missingPermissions: List<String>) : MainUiState()
    object AuthenticationRequired : MainUiState()
    data class Dashboard(val user: User, val children: List<Child>) : MainUiState()
    data class Error(val message: String) : MainUiState()
}