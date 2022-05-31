package com.example.testassigmentlogin.usecase

import com.example.testassigmentlogin.model.db.dto.UserDto
import timber.log.Timber
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val addUserToDatabaseUseCase: AddUserToDatabaseUseCase,
    private val checkIfUserExistsUseCase: CheckIfUserExistsUseCase
) {

    suspend operator fun invoke(email: String, password: String): Result {
        Timber.d("invoke: $email")
        val userExist = checkIfUserExistsUseCase(email)
        return if (!userExist) {
            addUserToDatabaseUseCase(UserDto(email = email, password = password))
            Timber.d("Success!!")
            Result.Success
        } else {
            Timber.d("Failure!!")
            Result.Failure
        }
    }

    sealed class Result{
        object Success: Result()
        object Failure: Result()
    }
}