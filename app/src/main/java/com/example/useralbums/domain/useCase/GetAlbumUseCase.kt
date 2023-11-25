package com.example.useralbums.domain.useCase

import com.example.useralbums.data.repo.MainRepoImpl
import com.example.useralbums.domain.repo.MainRepo

class GetAlbumUseCase() {
    private val repo = MainRepoImpl()
    suspend operator fun invoke(userId:Int) = repo.getAlbums(userId)
}