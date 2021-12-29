package com.coxtunes.androidarchitecturekotlin2021.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coxtunes.androidarchitecturekotlin2021.common.responseSealed.Resource
import com.coxtunes.androidarchitecturekotlin2021.domain.usecase.GetUsersUseCase
import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems
import com.coxtunes.androidarchitecturekotlin2021.ui.uistate.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: Nayeem Shiddiki Abir
 * @Date: 28-Dec-21
 */
@HiltViewModel
class UserViewModel @Inject constructor(private val usersUseCase: GetUsersUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchUsersData: Job? = null

    fun getUsers() {
        fetchUsersData?.cancel()

        fetchUsersData = viewModelScope.launch {
            val result: Resource<List<UsersViewItems>> = usersUseCase.getUsers()
            _uiState.update { it.copy(isLoading = true) }

            when(result){

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "",
                            userList = result.data ?: emptyList()
                        )
                    }
                }

                is Resource.Loading -> {
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            errorMessage = "",
                            userList = emptyList()
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.message ?: "Something went wrog.",
                            userList = emptyList()
                        )
                    }
                }

                is Resource.Empty -> {
                    UsersUiState()
                }

            }


        }

    }


}