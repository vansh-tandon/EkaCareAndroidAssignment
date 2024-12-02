package com.example.ekacareassignment.data.repository

import com.example.ekacareassignment.data.entity.UserEntity
import com.example.ekacareassignment.data.db.UserInfoDatabase
import com.example.ekacareassignment.data.mapper.toDomain
import com.example.ekacareassignment.domain.repository.UserRepository

class UserRepositoryImpl(private val db: UserInfoDatabase):UserRepository {
    override suspend fun insertUser(userEntity: UserEntity) = db.getUserDao().insertUser(userEntity)
    override suspend fun getAllUser() = db.getUserDao().getAllUsers().map { it.toDomain() }
}
