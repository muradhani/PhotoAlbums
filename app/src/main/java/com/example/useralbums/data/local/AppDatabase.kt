package com.example.useralbums.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.useralbums.data.Dto.photos.PhotosResponseItem

@Database(entities = [PhotosResponseItem::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }
}