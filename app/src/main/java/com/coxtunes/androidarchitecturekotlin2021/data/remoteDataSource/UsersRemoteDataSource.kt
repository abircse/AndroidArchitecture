package com.coxtunes.androidarchitecturekotlin2021.data.remoteDataSource

import com.coxtunes.androidarchitecturekotlin2021.common.responseSealed.Resource
import com.coxtunes.androidarchitecturekotlin2021.data.api.UsersApi
import com.coxtunes.androidarchitecturekotlin2021.data.mapper.toUsers
import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems
import javax.inject.Inject


class UsersRemoteDataSource @Inject constructor(private val api: UsersApi) {

    suspend fun getUsers(): Resource<List<UsersViewItems>> {
        return try {
            val response = api.getUsers()
            if (response.isSuccessful) {
                val users: List<UsersViewItems> = response.body()?.map {
                    it.toUsers()
                } ?: emptyList()
                Resource.Success(users)
            } else {
                Resource.Error(response.errorBody().toString())
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Could not get users data.")
        }
    }
}