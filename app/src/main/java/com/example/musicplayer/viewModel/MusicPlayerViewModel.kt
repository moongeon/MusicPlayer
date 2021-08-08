package com.example.musicplayer.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.musicplayer.model.Album
import com.example.musicplayer.network.MusicApi

import kotlinx.coroutines.launch
import java.lang.Exception

class MusicPlayerViewModel : ViewModel() {

    private val _Album = MutableLiveData<Album>()
    val Album: LiveData<Album> = _Album

    init {
        getAlbum()
    }

    private fun getAlbum(){
        viewModelScope.launch {
            try {
                _Album.value = MusicApi.retrofitService.getAlbum()
                var a = 0


            }catch (e : Exception){


            }




        }


    }


}