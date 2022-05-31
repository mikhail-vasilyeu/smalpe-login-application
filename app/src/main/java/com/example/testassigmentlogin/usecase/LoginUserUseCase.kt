package com.example.testassigmentlogin.usecase

import com.example.testassigmentlogin.model.domain.User
import com.example.testassigmentlogin.model.db.dao.UserDao
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val addLoggedInEmailToDatastoreUseCase: AddLoggedInEmailToDatastoreUseCase
) {

    suspend operator fun invoke(email: String, password: String) {
        try {
            val userDto = getUserByEmailUseCase(email)
            if (userDto.password != password) {
                //TODO: log
            }

            addLoggedInEmailToDatastoreUseCase(email)
        } catch (e: Exception) {
            //TODO: log
        }
    }
}