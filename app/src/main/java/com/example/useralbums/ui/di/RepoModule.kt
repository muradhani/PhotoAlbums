package com.example.useralbums.ui.di

import com.example.useralbums.data.mapper.UserMapper
import com.example.useralbums.data.remote.ApiService
import com.example.useralbums.data.repo.MainRepoImpl
import com.example.useralbums.domain.mapper.Mapper
import com.example.useralbums.domain.repo.MainRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideRepo(apiService: ApiService,mapper: UserMapper):MainRepo{
        return MainRepoImpl(apiService,mapper)
    }

}