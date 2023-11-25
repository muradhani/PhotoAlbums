package com.example.useralbums.data.remote

import com.example.useralbums.data.Dto.User.UserResponse
import com.example.useralbums.data.Dto.albums.AlbumsResponse
import com.example.useralbums.data.Dto.photos.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("albums")
    suspend fun getAlbums(@Query("userId") userId:Int):Response<AlbumsResponse>

    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId:Int):Response<PhotosResponse>

    @GET("users")
    suspend fun getUser(@Query("id") userId:Int):Response<UserResponse>
}