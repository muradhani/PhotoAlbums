package com.example.useralbums.domain.useCase

import android.content.Context
import com.example.useralbums.data.repo.MainRepoImpl
import com.example.useralbums.domain.repo.MainRepo
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val repo : MainRepo
) {

    suspend operator fun invoke(albumId: Int) =repo.getPhotos(albumId)
}