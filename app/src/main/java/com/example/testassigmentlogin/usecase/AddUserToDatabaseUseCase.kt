package com.example.testassigmentlogin.usecase

import com.example.testassigmentlogin.model.db.dao.UserDao
import com.example.testassigmentlogin.model.db.dto.UserDto
import timber.log.Timber
import javax.inject.Inject

class AddUserToDatabaseUseCase @Inject constructor(
    private val userDao: UserDao
) {

    operator fun invoke(userDto: UserDto) {
        Timber.d("invoke: $userDto")
        userDao.insert(userDto)
    }
}