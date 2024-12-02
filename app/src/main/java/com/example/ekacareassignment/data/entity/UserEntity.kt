package com.example.ekacareassignment.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    val age: Int,
    val dob: String,
    val address: String
)