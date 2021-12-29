package com.coxtunes.androidarchitecturekotlin2021.common.di

import androidx.databinding.ktx.BuildConfig
import com.coxtunes.androidarchitecturekotlin2021.data.api.UsersApi
import com.coxtunes.androidarchitecturekotlin2021.common.constants.BASE_URL
import com.coxtunes.androidarchitecturekotlin2021.common.utility.DispatcherProvider
import com.coxtunes.androidarchitecturekotlin2021.data.remoteDataSource.UsersRemoteDataSource
import com.coxtunes.androidarchitecturekotlin2021.data.repository.DefaultUserRepository
import com.coxtunes.androidarchitecturekotlin2021.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Singleton
    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        remoteDataSource: UsersRemoteDataSource,
        dispatcher: DispatcherProvider
    ): UserRepository {
        return DefaultUserRepository(remoteDataSource, dispatcher)
    }
}