package com.example.useralbums.ui.di

import com.example.useralbums.domain.repo.MainRepo
import com.example.useralbums.domain.useCase.GetAlbumUseCase
import com.example.useralbums.domain.useCase.GetPhotosUseCase
import com.example.useralbums.domain.useCase.GetUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//object UseCaseModule {
//    @Provides
//    fun provideGetUserUseCase(repo : MainRepo):GetUserUseCase{
//        return GetUserUseCase(repo)
//    }
//    @Provides
//    fun provideGetPhotosUseCase(repo : MainRepo):GetPhotosUseCase{
//        return GetPhotosUseCase(repo)
//    }
//
//    @Provides
//    fun provideGetAlbumsUseCase(repo : MainRepo):GetAlbumUseCase{
//        return GetAlbumUseCase(repo)
//    }
//}