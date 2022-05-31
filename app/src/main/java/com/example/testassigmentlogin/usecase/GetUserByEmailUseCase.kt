package com.example.testassigmentlogin.usecase

import com.example.testassigmentlogin.model.db.dao.UserDao
import com.example.testassigmentlogin.model.db.dto.UserDto
import timber.log.Timber
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(
    private val userDao: UserDao
) {

    suspend operator fun invoke(email: String): UserDto {
        Timber.d("invoke: $email")
        return userDao.findByEmail(email)
    }
}