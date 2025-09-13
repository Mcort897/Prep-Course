package com.parentalcontrolpro.ui.child

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
class ChildMainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<ChildMainUiState>(ChildMainUiState.Loading)
    val uiState: StateFlow<ChildMainUiState> = _uiState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    init {
        loadCurrentUser()
    }
    
    private fun loadCurrentUser() {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId()
            if (userId != null) {
                authRepository.getUserFromFirestore(userId)?.let { user ->
                    _currentUser.value = user
                    _uiState.value = ChildMainUiState.Success(user)
                } ?: run {
                    _uiState.value = ChildMainUiState.Error("Usuario no encontrado")
                }
            } else {
                _uiState.value = ChildMainUiState.LoggedOut
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
            _uiState.value = ChildMainUiState.LoggedOut
        }
    }
    
    fun refreshData() {
        loadCurrentUser()
    }
}

sealed class ChildMainUiState {
    object Loading : ChildMainUiState()
    data class Success(val user: User) : ChildMainUiState()
    data class Error(val message: String) : ChildMainUiState()
    object LoggedOut : ChildMainUiState()
}