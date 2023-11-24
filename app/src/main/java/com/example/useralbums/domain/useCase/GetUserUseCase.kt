package com.example.useralbums.domain.useCase

import com.example.useralbums.domain.repo.MainRepo

class GetUserUseCase (private val repo : MainRepo){
    suspend operator fun invoke(userId:Int) = repo.getUser(userId)

}