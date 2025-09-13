package com.parentalcontrolpro.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.parentalcontrolpro.databinding.ActivityAuthBinding
import com.parentalcontrolpro.ui.parent.ParentMainActivity
import com.parentalcontrolpro.ui.child.ChildMainActivity
import com.parentalcontrolpro.data.model.UserRole
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        binding.apply {
            btnLoginParent.setOnClickListener {
                showLoginForm(UserRole.PARENT)
            }
            
            btnLoginChild.setOnClickListener {
                showLoginForm(UserRole.CHILD)
            }
            
            btnRegisterParent.setOnClickListener {
                showRegisterForm(UserRole.PARENT)
            }
            
            btnRegisterChild.setOnClickListener {
                showRegisterForm(UserRole.CHILD)
            }
            
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.signIn(email, password)
                } else {
                    Toast.makeText(this@AuthActivity, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val confirmPassword = etConfirmPassword.text.toString().trim()
                val name = etName.text.toString().trim()
                
                if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && name.isNotEmpty()) {
                    if (password == confirmPassword) {
                        viewModel.register(email, password, name, viewModel.currentRole.value ?: UserRole.CHILD)
                    } else {
                        Toast.makeText(this@AuthActivity, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@AuthActivity, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            
            btnForgotPassword.setOnClickListener {
                val email = etEmail.text.toString().trim()
                if (email.isNotEmpty()) {
                    viewModel.resetPassword(email)
                } else {
                    Toast.makeText(this@AuthActivity, "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
                }
            }
            
            btnBack.setOnClickListener {
                showMainMenu()
            }
        }
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AuthUiState.Loading -> {
                        showLoading(true)
                    }
                    is AuthUiState.Success -> {
                        showLoading(false)
                        navigateToMainActivity(state.user.role)
                    }
                    is AuthUiState.Error -> {
                        showLoading(false)
                        Toast.makeText(this@AuthActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    is AuthUiState.Idle -> {
                        showLoading(false)
                    }
                }
            }
        }
        
        lifecycleScope.launch {
            viewModel.currentRole.collect { role ->
                updateUIForRole(role)
            }
        }
    }
    
    private fun showMainMenu() {
        binding.apply {
            layoutMainMenu.visibility = View.VISIBLE
            layoutLoginForm.visibility = View.GONE
            layoutRegisterForm.visibility = View.GONE
        }
    }
    
    private fun showLoginForm(role: UserRole) {
        viewModel.setCurrentRole(role)
        binding.apply {
            layoutMainMenu.visibility = View.GONE
            layoutLoginForm.visibility = View.VISIBLE
            layoutRegisterForm.visibility = View.GONE
            btnRegister.visibility = View.GONE
            etConfirmPassword.visibility = View.GONE
            etName.visibility = View.GONE
        }
    }
    
    private fun showRegisterForm(role: UserRole) {
        viewModel.setCurrentRole(role)
        binding.apply {
            layoutMainMenu.visibility = View.GONE
            layoutLoginForm.visibility = View.GONE
            layoutRegisterForm.visibility = View.VISIBLE
            btnLogin.visibility = View.GONE
            etConfirmPassword.visibility = View.VISIBLE
            etName.visibility = View.VISIBLE
        }
    }
    
    private fun updateUIForRole(role: UserRole) {
        binding.apply {
            when (role) {
                UserRole.PARENT -> {
                    tvTitle.text = "Iniciar Sesión como Padre"
                    btnLogin.text = "Iniciar Sesión"
                    btnRegister.text = "Registrarse"
                }
                UserRole.CHILD -> {
                    tvTitle.text = "Iniciar Sesión como Hijo"
                    btnLogin.text = "Iniciar Sesión"
                    btnRegister.text = "Registrarse"
                }
            }
        }
    }
    
    private fun showLoading(show: Boolean) {
        binding.apply {
            progressBar.visibility = if (show) View.VISIBLE else View.GONE
            btnLogin.isEnabled = !show
            btnRegister.isEnabled = !show
        }
    }
    
    private fun navigateToMainActivity(role: UserRole) {
        val intent = when (role) {
            UserRole.PARENT -> Intent(this, ParentMainActivity::class.java)
            UserRole.CHILD -> Intent(this, ChildMainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}