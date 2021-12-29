package com.coxtunes.androidarchitecturekotlin2021.data.dto.user

data class UsersItem(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)