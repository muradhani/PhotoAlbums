package com.example.useralbums.data.repo

import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.data.mapper.UserMapper
import com.example.useralbums.data.remote.ApiService
import com.example.useralbums.domain.models.State
import com.example.useralbums.domain.models.User
import com.example.useralbums.domain.repo.MainRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepoImpl(private val apiService: ApiService,private val mapper: UserMapper):MainRepo {
    override suspend fun getUser(userid:Int): Flow<State<User>>{
        return flow {
            emit(State.Loading)
            val result = apiService.getUser(userid)
            if (result.isSuccessful){
                val user = result.body()?.map {
                    mapper.map(it)
                }!!.first()
                emit(State.Success(user))
            }
            else{
                emit(State.Error(result.message()))
            }
        }
    }

    override suspend fun getAlbums(userid: Int): Flow<State<List<AlbumsResponseItem>>> {
        return flow {
            emit(State.Loading)
            val result = apiService.getAlbums(userid)
            if (result.isSuccessful){
                val albums = result.body()!!.toList()
                emit(State.Success(albums))
            }
            else{
                emit(State.Error(result.message()))
            }
        }
    }

    override suspend fun getPhotos(albumId: Int): Flow<State<User>> {
        return flow {

        }
    }
}