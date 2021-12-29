package com.coxtunes.androidarchitecturekotlin2021.data.api

import com.coxtunes.androidarchitecturekotlin2021.data.dto.user.Users
import retrofit2.Response
import retrofit2.http.GET


interface UsersApi {
    @GET("users")
    suspend fun getUsers(): Response<Users>
}