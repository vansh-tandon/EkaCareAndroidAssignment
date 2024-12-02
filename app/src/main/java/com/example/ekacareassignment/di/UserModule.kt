package com.example.ekacareassignment.di

import android.content.Context
import com.example.ekacareassignment.data.db.UserInfoDatabase
import com.example.ekacareassignment.data.repository.UserRepositoryImpl
import com.example.ekacareassignment.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserInfoDb(@ApplicationContext context: Context): UserInfoDatabase {
        return UserInfoDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(db: UserInfoDatabase): UserRepository {
        return UserRepositoryImpl(db)
    }
}
