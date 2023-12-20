package com.example.useralbums.domain.repo

import android.content.Context
import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.data.Dto.photos.PhotosResponseItem
import com.example.useralbums.domain.models.State
import com.example.useralbums.domain.models.User
import kotlinx.coroutines.flow.Flow

interface MainRepo {
    suspend fun getUser(userid:Int): Flow<State<User>>
    suspend fun getAlbums(userid:Int): Flow<State<List<AlbumsResponseItem>>>
    suspend fun getPhotos(albumId:Int): Flow<State<List<PhotosResponseItem>>>
    suspend fun photosearch(title: String):Flow<State<List<PhotosResponseItem>>>
}