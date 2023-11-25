package com.example.useralbums.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.useralbums.data.Dto.photos.PhotosResponseItem

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photo:PhotosResponseItem)
    @Delete
    fun delete(photo: PhotosResponseItem)

    @Query("SELECT * FROM photo WHERE title LIKE '%' || :title || '%'")
    fun searchPhotos(title: String): List<PhotosResponseItem>

    @Query("SELECT * FROM photo")
    fun getAll():List<PhotosResponseItem>

    @Query("DELETE FROM photo")
    fun deleteAll()
}