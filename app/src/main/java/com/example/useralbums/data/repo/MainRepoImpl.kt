package com.example.useralbums.data.repo

import android.content.Context
import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.data.Dto.photos.PhotosResponseItem
import com.example.useralbums.data.local.AppDatabase
import com.example.useralbums.data.local.PhotoDao
import com.example.useralbums.data.mapper.UserMapper
import com.example.useralbums.data.remote.ApiService
import com.example.useralbums.data.remote.RetrofitClient
import com.example.useralbums.domain.models.State
import com.example.useralbums.domain.models.User
import com.example.useralbums.domain.repo.MainRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


class MainRepoImpl (
    private val apiService: ApiService ,
    private val photoDao : PhotoDao,
    private val mapper: UserMapper
):MainRepo {
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

    override suspend fun getPhotos(albumId: Int): Flow<State<List<PhotosResponseItem>>> {
        return flow {

            emit(State.Loading)

            photoDao.deleteAll()
            try {

                val response = apiService.getPhotos(albumId)


                if (response.isSuccessful) {
                    val photosFromApi = response.body()
                    photosFromApi?.let {
                        it.forEach{
                            photoDao.insertAll(it)
                        }
                    }


                    emit(State.Success(photoDao.getAll()))
                } else {

                    emit(State.Error("Error fetching photos from the API"))
                }

            } catch (e: Exception) {

                emit(State.Error(e.message ?: "An error occurred"))
            }
        }
    }
    override suspend fun photosearch(title: String):Flow<State<List<PhotosResponseItem>>>{
        return flow {

            emit(State.Loading)

            try {

                val searchResult = photoDao.searchPhotos(title)


                emit(State.Success(searchResult))
            } catch (e: Exception) {

                emit(State.Error(e.message ?: "An error occurred"))
            }
        }
    }
    }
