package com.parentalcontrolpro.ui.child.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parentalcontrolpro.data.model.User
import com.parentalcontrolpro.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _remainingTime = MutableStateFlow(150) // 2.5 hours in minutes
    val remainingTime: StateFlow<Int> = _remainingTime.asStateFlow()
    
    init {
        loadUserData()
    }
    
    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            
            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                authRepository.getUserFromFirestore(currentUser.uid)?.let { user ->
                    _currentUser.value = user
                    _uiState.value = HomeUiState.Success(user)
                } ?: run {
                    _uiState.value = HomeUiState.Error("Usuario no encontrado")
                }
            } else {
                _uiState.value = HomeUiState.Error("Usuario no autenticado")
            }
        }
    }
    
    fun requestMoreTime() {
        viewModelScope.launch {
            // TODO: Implement request more time functionality
        }
    }
    
    fun requestAppAccess(packageName: String) {
        viewModelScope.launch {
            // TODO: Implement request app access functionality
        }
    }
    
    fun requestWebsiteAccess(url: String) {
        viewModelScope.launch {
            // TODO: Implement request website access functionality
        }
   }
    
    fun refreshData() {
        loadUserData()
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val user: User) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}