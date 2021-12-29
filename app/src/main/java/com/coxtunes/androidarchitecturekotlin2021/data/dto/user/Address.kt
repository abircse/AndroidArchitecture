package com.coxtunes.androidarchitecturekotlin2021.data.dto.user

data class Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)