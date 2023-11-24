package com.example.useralbums.domain.useCase

import com.example.useralbums.domain.repo.MainRepo

class GetPhotosUseCase(private val repo : MainRepo) {
    suspend operator fun invoke(){

    }
}