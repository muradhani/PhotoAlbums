package com.example.useralbums.domain.useCase

import com.example.useralbums.data.repo.MainRepoImpl
import com.example.useralbums.domain.repo.MainRepo

class GetUserUseCase (){
    private val repo = MainRepoImpl()
    suspend operator fun invoke(userId:Int) = repo.getUser(userId)

}