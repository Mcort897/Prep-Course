package com.safeguard.parental.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.safeguard.parental.data.repository.AuthRepository
import com.safeguard.parental.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val user: com.safeguard.parental.data.model.User? = null,
    val error: String? = null
)

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch.asStateFlow()
    
    init {
        checkAuthState()
    }
    
    fun checkFirstLaunch(context: Context) {
        val prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        _isFirstLaunch.value = prefs.getBoolean(Constants.PREF_FIRST_LAUNCH, true)
    }
    
    fun setFirstLaunchComplete(context: Context) {
        val prefs = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(Constants.PREF_FIRST_LAUNCH, false).apply()
        _isFirstLaunch.value = false
    }
    
    private fun checkAuthState() {
        viewModelScope.launch {
            authRepository.getAuthState().collect { isAuthenticated ->
                if (isAuthenticated) {
                    loadUserProfile()
                } else {
                    _authState.value = _authState.value.copy(
                        isAuthenticated = false,
                        user = null
                    )
                }
            }
        }
    }
    
    private suspend fun loadUserProfile() {
        _authState.value = _authState.value.copy(isLoading = true)
        
        authRepository.getCurrentUserProfile()
            .onSuccess { user ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    user = user,
                    error = null
                )
            }
            .onFailure { error ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = error.message
                )
            }
    }
    
    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(
                error = "Email y contraseña son requeridos"
            )
            return
        }
        
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                error = null
            )
            
            authRepository.signInWithEmailAndPassword(email, password)
                .onSuccess {
                    // Auth state will be updated automatically through the listener
                }
                .onFailure { error ->
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = getErrorMessage(error)
                    )
                }
        }
    }
    
    fun signUp(email: String, password: String, displayName: String) {
        if (email.isBlank() || password.isBlank() || displayName.isBlank()) {
            _authState.value = _authState.value.copy(
                error = "Todos los campos son requeridos"
            )
            return
        }
        
        if (password.length < 6) {
            _authState.value = _authState.value.copy(
                error = "La contraseña debe tener al menos 6 caracteres"
            )
            return
        }
        
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                error = null
            )
            
            authRepository.createUserWithEmailAndPassword(email, password, displayName)
                .onSuccess {
                    // Auth state will be updated automatically through the listener
                }
                .onFailure { error ->
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = getErrorMessage(error)
                    )
                }
        }
    }
    
    fun sendPasswordReset(email: String) {
        if (email.isBlank()) {
            _authState.value = _authState.value.copy(
                error = "Email es requerido"
            )
            return
        }
        
        viewModelScope.launch {
            _authState.value = _authState.value.copy(
                isLoading = true,
                error = null
            )
            
            authRepository.sendPasswordResetEmail(email)
                .onSuccess {
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = "Email de recuperación enviado"
                    )
                }
                .onFailure { error ->
                    _authState.value = _authState.value.copy(
                        isLoading = false,
                        error = getErrorMessage(error)
                    )
                }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
    
    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
    
    private fun getErrorMessage(error: Throwable): String {
        return when {
            error.message?.contains("network") == true -> "Error de conexión a internet"
            error.message?.contains("invalid-email") == true -> "Email inválido"
            error.message?.contains("user-not-found") == true -> "Usuario no encontrado"
            error.message?.contains("wrong-password") == true -> "Contraseña incorrecta"
            error.message?.contains("email-already-in-use") == true -> "Email ya está en uso"
            error.message?.contains("weak-password") == true -> "Contraseña muy débil"
            else -> error.message ?: "Error desconocido"
        }
    }
    
    class Factory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                return AuthViewModel(authRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}