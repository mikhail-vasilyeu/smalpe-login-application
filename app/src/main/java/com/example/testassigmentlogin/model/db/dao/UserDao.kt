package com.example.testassigmentlogin.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.testassigmentlogin.model.db.dto.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM UserDto WHERE email=:email")
    fun findByEmail(email: String): UserDto

    @Query("")
    fun isRowIsExist(email: String)

    @Insert
    fun insert(user: UserDto)
}