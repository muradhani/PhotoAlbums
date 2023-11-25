package com.example.useralbums.domain.useCase

import com.example.useralbums.data.repo.MainRepoImpl
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val repo : MainRepoImpl
) {

    suspend operator fun invoke(Imagetitle: kotlin.String, context: android.content.Context?) = repo.photosearch(Imagetitle,context)
}