package com.coxtunes.androidarchitecturekotlin2021.common.di

import com.coxtunes.androidarchitecturekotlin2021.domain.repository.UserRepository
import com.coxtunes.androidarchitecturekotlin2021.domain.usecase.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideGetUsersUseCase(repo: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(repo)
    }
}