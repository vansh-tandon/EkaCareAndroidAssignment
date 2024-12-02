package com.example.ekacareassignment.domain.repository

import com.example.ekacareassignment.data.entity.UserEntity
import com.example.ekacareassignment.domain.model.User

interface UserRepository {
    suspend fun insertUser(userEntity: UserEntity)
    suspend fun getAllUser(): List<User>
}