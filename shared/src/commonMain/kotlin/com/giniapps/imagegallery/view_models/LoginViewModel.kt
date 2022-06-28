package com.giniapps.imagegallery.view_models

import com.giniapps.imagegallery.data.interfaces.DataRepository
import com.giniapps.imagegallery.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: DataRepository
): SharedViewModel() {
    val usersState = MutableStateFlow(Users(emptyList())).asCommonFlow()
    init {
        coroutineScope.launch {
            val users = repository.getUsers()
            usersState.emit(Users(users))
        }
    }

    fun loginUser(userId: Long) {
        coroutineScope.launch {
            repository.login(userId)
        }
    }
}

data class Users(
    val list: List<User>
)