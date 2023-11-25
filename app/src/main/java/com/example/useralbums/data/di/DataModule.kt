package com.example.useralbums.data.di

import android.content.Context
import android.provider.ContactsContract.Contacts.Photo
import androidx.room.Room
import com.example.useralbums.data.local.AppDatabase
import com.example.useralbums.data.local.PhotoDao
import com.example.useralbums.data.remote.ApiService
import com.example.useralbums.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideApiservice(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    fun okhttp():OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRoomDao(
        db:AppDatabase
    ):PhotoDao{
        return db.photoDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabsae(
        @ApplicationContext context: Context
    ):AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

}