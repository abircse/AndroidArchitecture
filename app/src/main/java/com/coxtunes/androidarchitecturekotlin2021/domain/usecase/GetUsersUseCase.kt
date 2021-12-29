package com.coxtunes.androidarchitecturekotlin2021.domain.usecase

import com.coxtunes.androidarchitecture2021.common.responseSealed.Resource
import com.coxtunes.androidarchitecturekotlin2021.domain.repository.UserRepository
import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems
import javax.inject.Inject

/**
 * @Author: Nayeem Shiddiki Abir
 * @Date: 28-Dec-21
 */
class GetUsersUseCase @Inject constructor(private val userRepo: UserRepository) {

    suspend fun getUsers(): Resource<List<UsersViewItems>> {
        return userRepo.getUsersList()
    }
}