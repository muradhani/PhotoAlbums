package com.example.useralbums.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.useralbums.data.Dto.photos.PhotosResponseItem
import com.example.useralbums.domain.models.State
import com.example.useralbums.domain.useCase.GetPhotosUseCase
import com.example.useralbums.domain.useCase.SearchPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsFragmentViewModel @Inject constructor(
    val getPhotosUseCase :GetPhotosUseCase,
    val searchPhotosUseCase : SearchPhotosUseCase
):ViewModel() {
    private val _photos = MutableLiveData<State<List<PhotosResponseItem>>>()
    val photos : LiveData<State<List<PhotosResponseItem>>> = _photos

    fun getPhotos(albumId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Call the GetPhotosUseCase to get photos from the repository
                getPhotosUseCase.invoke(albumId).collect{
                    Log.i("main",it.toData().toString())
                    _photos.postValue(it)
                }

            } catch (e: Exception) {
                _photos.postValue(State.Error(e.message ?: "An error occurred"))
            }
        }
    }
    fun search(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Call the SearchPhotosUseCase to search for photos by title
                val result = searchPhotosUseCase.invoke(title).collect{
                    if (it is State.Success){
                        _photos.postValue(it)
                    }

                }

            } catch (e: Exception) {
                _photos.postValue(State.Error(e.message ?: "An error occurred"))
            }
        }
    }
}