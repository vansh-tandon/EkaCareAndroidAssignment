package com.example.ekacareassignment.data.mapper

import com.example.ekacareassignment.data.entity.UserEntity
import com.example.ekacareassignment.domain.model.User

// Converts User to UserEntity
fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name,
        age = this.age?.toInt() ?: 0,
        dob = this.dob,
        address = this.address
    )
}

// Converts UserEntity to User
fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        name = this.name,
        age = (this?.age ?: "").toString(),
        dob = this.dob,
        address = this.address
    )
}
