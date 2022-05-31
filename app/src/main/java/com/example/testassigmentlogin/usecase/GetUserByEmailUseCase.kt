package com.example.testassigmentlogin.usecase

import com.example.testassigmentlogin.model.db.dao.UserDao
import com.example.testassigmentlogin.model.db.dto.UserDto
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(
    private val userDao: UserDao
) {

    operator fun invoke(email: String): UserDto {
        return userDao.findByEmail(email)
    }
}