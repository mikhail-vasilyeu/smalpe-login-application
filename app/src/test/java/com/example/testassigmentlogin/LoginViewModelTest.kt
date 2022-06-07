package com.example.testassigmentlogin

import app.cash.turbine.test
import com.example.testassigmentlogin.screens.login.LoginErrorType
import com.example.testassigmentlogin.screens.login.LoginState
import com.example.testassigmentlogin.screens.login.LoginViewModel
import com.example.testassigmentlogin.usecase.GetForgottenPasswordUseCase
import com.example.testassigmentlogin.usecase.LoginUserUseCase
import com.example.testassigmentlogin.usecase.RegisterUserUseCase
import com.example.testassigmentlogin.usecase.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class LoginViewModelTest {

    private lateinit var loginUserUseCase: LoginUserUseCase
    private lateinit var registerUserUseCase: RegisterUserUseCase
    private lateinit var getForgottenPasswordUseCase: GetForgottenPasswordUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)

    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    private fun setupViewModel(
        loginUserUseCase: LoginUserUseCase = mock(LoginUserUseCase::class.java),
        registerUserUseCase: RegisterUserUseCase = mock(RegisterUserUseCase::class.java),
        getForgottenPasswordUseCase: GetForgottenPasswordUseCase = mock(GetForgottenPasswordUseCase::class.java)
    ): LoginViewModel {
        this.loginUserUseCase = loginUserUseCase
        this.registerUserUseCase = registerUserUseCase
        this.getForgottenPasswordUseCase = getForgottenPasswordUseCase

        return LoginViewModel(
            this.loginUserUseCase,
            this.registerUserUseCase,
            this.getForgottenPasswordUseCase
        )
    }

    @Test
    fun loginClicked_validateInputsErrors() {
        val viewModel = setupViewModel()
        val invalidUserName = ""
        val invalidPassword = ""

        runTest {
            viewModel.state.test {
                assertEquals(LoginState(), awaitItem())
                viewModel.loginClicked(invalidUserName, invalidPassword)
                verify(loginUserUseCase, never()).invoke(invalidUserName, invalidPassword)
                assertEquals(LoginState(false, false), awaitItem())
            }
        }
    }

    @Test
    fun loginClicked_inputsGood_loginError() {
        val viewModel = setupViewModel(
            loginUserUseCase = mock(
                LoginUserUseCase::class.java
            ) {
                LoginUserUseCase.Result.Failure
            }
        )
        val validUserName = "test@gmail.com"
        val validPassword = "123456"

        runTest {
            viewModel.error.test {
                viewModel.loginClicked(validUserName, validPassword)
                verify(loginUserUseCase, atLeastOnce()).invoke(validUserName, validPassword)
                assertEquals(LoginErrorType.LOGIN, awaitItem())
            }

        }

    }

    @Test
    fun loginClicked_inputsGood_loginSuccess() {
        val viewModel = setupViewModel(
            loginUserUseCase = mock(
                LoginUserUseCase::class.java
            ) {
                LoginUserUseCase.Result.Success
            }
        )

        val invalidUserName = ""
        val invalidPassword = ""

        val userName = "2@email.com"
        val password = "12345"

        runTest {
            viewModel.state.test {
                assertEquals(LoginState(), awaitItem())
                viewModel.loginClicked(invalidUserName, invalidPassword)
                verify(loginUserUseCase, never()).invoke(invalidUserName, invalidPassword)
                assertEquals(LoginState(false, false), awaitItem())

                viewModel.loginClicked(userName, password)
                assertEquals(LoginState(), awaitItem())
            }

            viewModel.error.test {
                viewModel.loginClicked(userName, password)
                expectNoEvents()
            }

            viewModel.navigateToApp.test {
                viewModel.loginClicked(userName, password)
                assertEquals(Unit, awaitItem())
            }

            verify(loginUserUseCase, atLeast(3)).invoke(userName, password)
        }
    }


    @Test
    fun signupClicked_inputsGood_signupError() {
        val viewModel = setupViewModel(registerUserUseCase = mock(
            RegisterUserUseCase::class.java
        ) {
            RegisterUserUseCase.Result.Failure
        })
        val userName = "2@email.com"
        val password = "12345"
        runTest {
            viewModel.error.test {
                viewModel.signUpClicked(userName, password)
                verify(registerUserUseCase, atLeastOnce()).invoke(userName, password)
                assertEquals(LoginErrorType.SIGNUP, awaitItem())
            }
        }
    }

    @Test
    fun signUpClicked_inputsGood_registerSuccess() {
        val viewModel = setupViewModel(
            loginUserUseCase = mock(
                LoginUserUseCase::class.java
            ) {
                LoginUserUseCase.Result.Success
            },
            registerUserUseCase = mock(
                RegisterUserUseCase::class.java
            ) {
                RegisterUserUseCase.Result.Success
            })
        val invalidUsername = ""
        val invalidPassword = ""
        val userName = "2@email.com"
        val password = "12345"
        runTest {
            viewModel.state.test {
                assertEquals(LoginState(), awaitItem())
                viewModel.signUpClicked(invalidUsername, invalidPassword)
                assertEquals(LoginState(false, false), awaitItem())
                viewModel.signUpClicked(userName, password)
                assertEquals(LoginState(), awaitItem())
            }
            viewModel.error.test {
                viewModel.signUpClicked(userName, password)
                expectNoEvents()
            }
            viewModel.registerSuccess.test {
                viewModel.signUpClicked(userName, password)
                assertEquals(Unit, awaitItem())
            }
            verify(registerUserUseCase, atLeast(3)).invoke(userName, password)
            verify(loginUserUseCase, atLeast(3)).invoke(userName, password)
        }
    }

    @Test
    fun forgotPasswordClicked() {
        val viewModel = setupViewModel()
        runTest {
            viewModel.bottomSheetShow.test {
                viewModel.forgotPasswordClicked()
                assertEquals(Unit, awaitItem())
            }
        }
    }

    @Test
    fun forgotPasswordSubmitClicked_success() {
        val userName = "2@email.com"
        val password = "12345"

        val viewModel =
            setupViewModel(getForgottenPasswordUseCase = mock(GetForgottenPasswordUseCase::class.java) {
                Result.Success(password)
            })

        runTest {
            viewModel.forgotPasswordGetSuccess.test {
                viewModel.forgotPasswordSubmitClicked(userName)
                verify(getForgottenPasswordUseCase, atLeast(1)).invoke(userName)
                assertEquals(password, awaitItem())
            }
        }
    }

    @Test
    fun forgotPasswordSubmitClicked_failure() {
        val userName = "2@email.com"

        val viewModel =
            setupViewModel(
                getForgottenPasswordUseCase = mock(GetForgottenPasswordUseCase::class.java) {
                    Result.Failure
                }
            )
        runTest {
            viewModel.error.test {
                viewModel.forgotPasswordSubmitClicked(userName)
                verify(getForgottenPasswordUseCase, atLeastOnce()).invoke(userName)
                assertEquals(LoginErrorType.FORGOT_PASSWORD, awaitItem())
            }
        }
    }

    @Test
    fun onRegistrationSnackbarDismissed() {
        val viewModel = setupViewModel()
        runTest {
            viewModel.navigateToApp.test {
                viewModel.onRegistrationSnackbarDismissed()
                assertEquals(Unit, awaitItem())
            }
        }
    }

}