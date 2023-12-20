package com.example.useralbums.domain.useCase

import com.example.useralbums.data.repo.MainRepoImpl
import com.example.useralbums.domain.repo.MainRepo
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repo : MainRepo
){

    suspend operator fun invoke(userId:Int) = repo.getUser(userId)

}