package com.example.testassigmentlogin.usecase

import timber.log.Timber
import javax.inject.Inject

open class GetForgottenPasswordUseCase @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) {

    open suspend operator fun invoke(email: String): String {
        Timber.d(email)
        return getUserByEmailUseCase(email).password

    }
}