package com.example.testassigmentlogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testassigmentlogin.usecase.LoginUserUseCase
import com.example.testassigmentlogin.usecase.RegisterUserUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class Activity : AppCompatActivity() {

    @Inject
    lateinit var loginUserUseCase: LoginUserUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = "test@email.com"
        val password = "querty123"
        lifecycleScope.launch { loginUserUseCase(email, password) }

    }
}