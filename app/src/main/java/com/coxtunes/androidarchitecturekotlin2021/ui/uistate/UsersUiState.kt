package com.coxtunes.androidarchitecturekotlin2021.ui.uistate

import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems


data class UsersUiState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val userList: List<UsersViewItems> = emptyList(),
)
