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

class MainRepoImpl():MainRepo {
    private val apiService: ApiService = RetrofitClient.getClient()
    private val mapper: UserMapper = UserMapper()

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

    override suspend fun getPhotos(albumId: Int,context:Context): Flow<State<List<PhotosResponseItem>>> {
        return flow {
// Emit loading state
            emit(State.Loading)
            val photoDao : PhotoDao = AppDatabase.getInstance(context).photoDao()
            photoDao.deleteAll()
            try {
                // 1. Fetch photos from the API
                val response = apiService.getPhotos(albumId)

                // 2. Check if the API call was successful
                if (response.isSuccessful) {
                    // 3. Cache the photos in the Room database
                    val photosFromApi = response.body()
                    photosFromApi?.let {
                        it.forEach{
                            photoDao.insertAll(it)
                        }
                    }

                    // 4. Emit success state with the cached photos
                    emit(State.Success(photoDao.getAll()))
                } else {
                    // 5. Emit error state with the error message
                    emit(State.Error("Error fetching photos from the API"))
                }

            } catch (e: Exception) {
                // 6. Emit error state with the exception message
                emit(State.Error(e.message ?: "An error occurred"))
            }
        }
    }
    suspend fun photosearch(title: String, context: Context?):Flow<State<List<PhotosResponseItem>>>{
        return flow {
            // Emit loading state
            val photoDao : PhotoDao = AppDatabase.getInstance(context!!).photoDao()
            emit(State.Loading)

            try {
                // 1. Perform the search in the Room database
                val searchResult = photoDao.searchPhotos(title)

                // 2. Emit success state with the search result
                emit(State.Success(searchResult))
            } catch (e: Exception) {
                // 3. Emit error state with the exception message
                emit(State.Error(e.message ?: "An error occurred"))
            }
        }
    }
    }
