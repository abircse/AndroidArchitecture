package com.coxtunes.androidarchitecturekotlin2021.data.repository

import com.coxtunes.androidarchitecturekotlin2021.common.responseSealed.Resource
import com.coxtunes.androidarchitecturekotlin2021.data.remoteDataSource.UsersRemoteDataSource
import com.coxtunes.androidarchitecturekotlin2021.domain.repository.UserRepository
import com.coxtunes.androidarchitecturekotlin2021.domain.viewobjects.UsersViewItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

/**
 * @Author: Nayeem Shiddiki Abir
 */
class DefaultUserRepository @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource,
    private val externalScope: CoroutineScope
) : UserRepository {

    // Mutex to make writes to cached values thread-safe.
    private val latestUsersMutex = Mutex()

    // Cache of the latest news got from the network.
    private var latestUsersList: Resource<List<UsersViewItems>> = Resource.Empty()

    override suspend fun getUsersList(refresh: Boolean): Resource<List<UsersViewItems>> {
        return if (latestUsersList.data == null) {
            externalScope.async {
                remoteDataSource.getUsers().also { userlist ->
                    // Thread-safe write to latestNews
                    latestUsersMutex.withLock {
                        this@DefaultUserRepository.latestUsersList = userlist
                    }
                }
            }.await()
        } else {
            latestUsersMutex.withLock { latestUsersList }
        }
    }
}