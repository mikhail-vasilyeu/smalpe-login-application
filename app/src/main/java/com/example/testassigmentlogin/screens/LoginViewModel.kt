package com.example.testassigmentlogin.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassigmentlogin.usecase.LoginUserUseCase
import com.example.testassigmentlogin.utils.isValidEmail
import com.example.testassigmentlogin.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _error = MutableStateFlow(Unit)
    val error: Flow<Unit> = _error

    private val _navigateToApp = MutableStateFlow(Unit)
    val navigateToApp: Flow<Unit> = _navigateToApp

    private val _state = MutableStateFlow(LoginState())
    val state: Flow<LoginState> = _state

    fun loginClicked(email: String, password: String) {
        if (validateInputs(email, password)) {
            viewModelScope.launch {
                when (loginUserUseCase(email, password)) {
                    LoginUserUseCase.Result.Failure -> {
                        _error.value = Unit
                    }
                    LoginUserUseCase.Result.Success -> {
                        _navigateToApp.value = Unit
                    }
                }
            }
        }
    }

    fun signUpClicked(email: String, password: String) {
        if (validateInputs(email, password)) {

        }
    }

    fun forgotPasswordClicked() {

    }

    private fun validateInputs(email: String, password: String): Boolean {
        val isEmailValid = email.isValidEmail()
        val isPasswordValid = password.isValidPassword()
        _state.value =
            _state.value.copy(isEmailValid = isEmailValid, isPasswordValid = isPasswordValid)

        return isEmailValid && isPasswordValid

    }
}