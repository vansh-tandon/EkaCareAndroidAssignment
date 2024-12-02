package com.example.ekacareassignment.presentation.userInfoScreen

sealed class UserFormEvent {
    data class EnteredName(val name: String) : UserFormEvent()
    data class EnteredAge(val age: String) : UserFormEvent()
    data class EnteredDOB(val dob: String) : UserFormEvent()
    data class EnteredAddress(val address: String) : UserFormEvent()
    object Submit : UserFormEvent()
    object GetAllUsers : UserFormEvent()
}
