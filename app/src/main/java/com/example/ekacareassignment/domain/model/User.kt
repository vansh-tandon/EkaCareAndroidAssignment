package com.example.ekacareassignment.domain.model

data class User(
    val id: Int = 0,
    var name: String,
    val age: String?,
    val dob: String,
    val address: String
)