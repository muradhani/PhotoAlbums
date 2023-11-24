package com.example.useralbums.domain.useCase

import com.example.useralbums.domain.repo.MainRepo

class GetAlbumUseCase(private val repo : MainRepo) {
    suspend operator fun invoke(userId:Int) = repo.getAlbums(userId)
}