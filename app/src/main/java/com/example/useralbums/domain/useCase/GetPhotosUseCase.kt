package com.example.useralbums.domain.useCase

import android.content.Context
import com.example.useralbums.data.repo.MainRepoImpl
import com.example.useralbums.domain.repo.MainRepo

class GetPhotosUseCase() {
    private val repo = MainRepoImpl()
    suspend operator fun invoke(albumId: Int, context: Context?) =
        context?.let { repo.getPhotos(albumId, it) }
}