package com.example.testassigmentlogin

import app.cash.turbine.test
import com.example.testassigmentlogin.screens.login.LoginState
import com.example.testassigmentlogin.screens.login.LoginViewModel
import com.example.testassigmentlogin.usecase.GetForgottenPasswordUseCase
import com.example.testassigmentlogin.usecase.LoginUserUseCase
import com.example.testassigmentlogin.usecase.RegisterUserUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class LoginViewModelTest {

    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var getForgottenPasswordUseCase: GetForgottenPasswordUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        loginUserUseCase = mock(LoginUserUseCase::class.java)
        registerUserUseCase = mock(RegisterUserUseCase::class.java)
        getForgottenPasswordUseCase = mock(GetForgottenPasswordUseCase::class.java)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loginClicked_validateInputsErrors() {
        val viewModel = LoginViewModel(
            loginUserUseCase,
            registerUserUseCase,
            getForgottenPasswordUseCase
        )
        val invalidUserName = ""
        val invalidPassword = ""
        viewModel.loginClicked(invalidUserName, invalidPassword)

        runTest {
            viewModel.state.test {
                assertEquals(LoginState(false, false), awaitItem())
            }
        }
    }
}