package com.example.useralbums.ui.di

import com.example.useralbums.data.mapper.UserMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    fun provideUserMapper(): UserMapper {
        return UserMapper()
    }
}