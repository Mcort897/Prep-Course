package com.parentalcontrolpro.ui.parent

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
class ParentMainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<ParentMainUiState>(ParentMainUiState.Loading)
    val uiState: StateFlow<ParentMainUiState> = _uiState.asStateFlow()
    
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
                    _uiState.value = ParentMainUiState.Success(user)
                } ?: run {
                    _uiState.value = ParentMainUiState.Error("Usuario no encontrado")
                }
            } else {
                _uiState.value = ParentMainUiState.LoggedOut
            }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
            _uiState.value = ParentMainUiState.LoggedOut
        }
    }
    
    fun refreshData() {
        loadCurrentUser()
    }
}

sealed class ParentMainUiState {
    object Loading : ParentMainUiState()
    data class Success(val user: User) : ParentMainUiState()
    data class Error(val message: String) : ParentMainUiState()
    object LoggedOut : ParentMainUiState()
}