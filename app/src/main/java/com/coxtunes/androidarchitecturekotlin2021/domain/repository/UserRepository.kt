package com.coxtunes.androidarchitecturekotlin2021.domain.repository

import com.coxtunes.androidarchitecturekotlin2021.common.responseSealed.Resource
import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems


interface UserRepository {
    suspend fun getUsersList(refresh: Boolean = false): Resource<List<UsersViewItems>>
}