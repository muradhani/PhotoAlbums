package com.example.useralbums.ui.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.useralbums.data.Dto.albums.AlbumsResponse
import com.example.useralbums.data.Dto.albums.AlbumsResponseItem
import com.example.useralbums.domain.models.State
import com.example.useralbums.domain.models.User
import com.example.useralbums.domain.repo.MainRepo
import com.example.useralbums.domain.useCase.GetAlbumUseCase
import com.example.useralbums.domain.useCase.GetUserUseCase
import com.example.useralbums.ui.adapter.AlbumListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getAlbumUseCase: GetAlbumUseCase,
):ViewModel(){
    private val _albumsList = MutableLiveData<State<List<AlbumsResponseItem>>>()
    val albumList : LiveData<State<List<AlbumsResponseItem>>> = _albumsList

    private val _user = MutableLiveData<State<User>>()
    val user : LiveData<State<User>> = _user
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getRandomUser()
        }
    }
    suspend fun getRandomUser(){
        getUserUseCase(1).collect{
            withContext(Dispatchers.Main){
                _user.postValue(it)
            }
            if (it is State.Success){
                Log.i("main",it.toData()!!.id.toString())
                it.toData()?.let { it1 -> getAlbums(it1.id) }
            }
        }
    }
    suspend fun getAlbums(userid:Int){
        getAlbumUseCase(userid).collect{
            withContext(Dispatchers.Main){
                _albumsList.postValue(it)
            }
        }
    }

}