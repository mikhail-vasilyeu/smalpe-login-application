package com.example.testassigmentlogin.screens

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testassigmentlogin.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModels()
}