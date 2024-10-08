package com.hamzaazman.passwordgenerator.di

import com.hamzaazman.passwordgenerator.data.repository.MainRepositoryImpl
import com.hamzaazman.passwordgenerator.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMainRepository(repositoryImpl: MainRepositoryImpl): MainRepository
}