package com.example.useralbums.data.Dto.photos

data class PhotosResponseItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)