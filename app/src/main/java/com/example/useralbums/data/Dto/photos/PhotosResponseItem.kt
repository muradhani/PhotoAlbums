package com.example.useralbums.data.Dto.photos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class PhotosResponseItem(
    val albumId: Int,
    @PrimaryKey
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)