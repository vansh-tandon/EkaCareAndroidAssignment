package com.example.ekacareassignment.presentation.userInfoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ekacareassignment.core.utils.ADDRESS_CANNOT_BE_EMPTY
import com.example.ekacareassignment.core.utils.AGE_MUST_BE_A_VALID_POSITIVE_NUMBER
import com.example.ekacareassignment.core.utils.DATE_OF_BIRTH_CANNOT_BE_EMPTY
import com.example.ekacareassignment.core.utils.FORM_VALIDATION_FAILED
import com.example.ekacareassignment.core.utils.USER_INFORMATION_SAVED_SUCCESSFULLY
import com.example.ekacareassignment.core.utils.NAME_CANNOT_BE_EMPTY
import com.example.ekacareassignment.data.mapper.toEntity
import com.example.ekacareassignment.domain.model.User
import com.example.ekacareassignment.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private var _user = MutableStateFlow(User(0,"","","",""))
    val user: StateFlow<User> get() = _user

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    fun onEvent(event: UserFormEvent) {
        when (event) {
            is UserFormEvent.EnteredName -> {
                _user.value = _user.value.copy(name = event.name)
            }
            is UserFormEvent.EnteredAge -> {
                _user.value = _user.value.copy(age = event.age)
            }
            is UserFormEvent.EnteredDOB -> {
                _user.value = _user.value.copy(dob = event.dob)
            }
            is UserFormEvent.EnteredAddress -> {
                _user.value = _user.value.copy(address = event.address)
            }
            UserFormEvent.Submit -> submitForm()
            UserFormEvent.GetAllUsers -> fetchAndDisplayUserCount()
        }
    }

    private fun submitForm() {
        val errors = mutableListOf<String>()

        if (user.value.name.isBlank()) {
            errors.add(NAME_CANNOT_BE_EMPTY)
        }

        val age = user.value.age?.toIntOrNull()
        if (age == null || age < 0) {
            errors.add(AGE_MUST_BE_A_VALID_POSITIVE_NUMBER)
        }

        if (user.value.dob.isBlank()) {
            errors.add(DATE_OF_BIRTH_CANNOT_BE_EMPTY)
        }

        if (user.value.address.isBlank()) {
            errors.add(ADDRESS_CANNOT_BE_EMPTY)
        }

        if (errors.isEmpty()) {
            viewModelScope.launch {
                repository.insertUser(user.value.toEntity())
                sendMessage("$USER_INFORMATION_SAVED_SUCCESSFULLY: ${user.value}")
            }
        } else {
            sendMessage("$FORM_VALIDATION_FAILED:\n${errors.joinToString("\n")}")
        }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    private fun fetchAndDisplayUserCount(){
        viewModelScope.launch {
            val users = repository.getAllUser()
            _toastMessage.emit("Fetched ${users.size} users.")
        }
    }
}