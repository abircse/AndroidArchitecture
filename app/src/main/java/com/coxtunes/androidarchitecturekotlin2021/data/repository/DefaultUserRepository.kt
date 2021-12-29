package com.coxtunes.androidarchitecturekotlin2021.data.repository

import com.coxtunes.androidarchitecture2021.common.responseSealed.Resource
import com.coxtunes.androidarchitecture2021.common.utility.DispatcherProvider
import com.coxtunes.androidarchitecturekotlin2021.data.remoteDataSource.UsersRemoteDataSource
import com.coxtunes.androidarchitecturekotlin2021.domain.repository.UserRepository
import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @Author: Nayeem Shiddiki Abir
 * @Date: 28-Dec-21
 */
class DefaultUserRepository @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource,
    private val dispatcher: DispatcherProvider
) : UserRepository {

    // Mutex to make writes to cached values thread-safe.
    private val latestUsersMutex = Mutex()

    // Cache of the latest news got from the network.
    private var latestUsersList: Resource<List<UsersViewItems>> = Resource.Empty()

    override suspend fun getUsersList(refresh: Boolean): Resource<List<UsersViewItems>> {
        withContext(dispatcher.io) {
            val itemsRes = remoteDataSource.getUsers()

            // Thread-safe write to latestNews
            latestUsersMutex.withLock {
                this@DefaultUserRepository.latestUsersList = itemsRes
            }
        }
        return latestUsersMutex.withLock { latestUsersList }
    }

}