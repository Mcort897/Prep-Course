package com.parentalcontrolpro.ui.parent.dashboard

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
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    private val _children = MutableStateFlow<List<User>>(emptyList())
    val children: StateFlow<List<User>> = _children.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            
            val currentUser = authRepository.getCurrentUser()
            if (currentUser != null) {
                authRepository.getChildrenByParentId(currentUser.uid)
                    .onSuccess { childrenList ->
                        _children.value = childrenList
                        _uiState.value = DashboardUiState.Success(childrenList)
                    }
                    .onFailure { exception ->
                        _uiState.value = DashboardUiState.Error(exception.message ?: "Error al cargar datos")
                    }
            } else {
                _uiState.value = DashboardUiState.Error("Usuario no autenticado")
            }
        }
    }
    
    fun refreshData() {
        loadDashboardData()
    }
}

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val children: List<User>) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}