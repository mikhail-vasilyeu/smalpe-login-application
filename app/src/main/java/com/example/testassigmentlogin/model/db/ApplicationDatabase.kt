package com.example.testassigmentlogin.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testassigmentlogin.model.db.dto.UserDto
import com.example.testassigmentlogin.model.db.dao.UserDao

@Database(entities = arrayOf(UserDto::class), version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}