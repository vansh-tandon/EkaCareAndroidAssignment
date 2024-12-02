package com.example.ekacareassignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ekacareassignment.data.entity.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<UserEntity>
}
