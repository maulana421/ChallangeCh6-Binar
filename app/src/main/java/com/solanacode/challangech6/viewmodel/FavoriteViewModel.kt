package com.solanacode.challangech6.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solanacode.challangech6.model.MovieFav
import com.solanacode.challangech6.room.DaoFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val dao : DaoFavorite): ViewModel() {

    val getmovieFavorite : MutableLiveData<MutableList<MovieFav>> = MutableLiveData()
    val postmovieFavorite : MutableLiveData<Unit> = MutableLiveData()
    val deletemovieFavorite : MutableLiveData<Unit> = MutableLiveData()
    val checkmovieFav : MutableLiveData<Int> = MutableLiveData()


    fun getMovieFav() : LiveData<MutableList<MovieFav>> = getmovieFavorite
    fun postMovieFav() : LiveData<Unit> = postmovieFavorite
    fun deleteMovieFav() : LiveData<Unit> = deletemovieFavorite
    fun checkmovieFav() : LiveData<Int> = checkmovieFav

    fun getlistmovieFavorite(){
        CoroutineScope(Dispatchers.IO).launch {
            getmovieFavorite.postValue(dao.getMovieFavorite())
        }
    }

    fun postUser(movie: MovieFav){
        CoroutineScope(Dispatchers.IO).launch {
            postmovieFavorite.postValue(dao.insertFavorite(movie))
        }
    }

    fun deleteUser(movie: MovieFav){
        CoroutineScope(Dispatchers.IO).launch {
            deletemovieFavorite.postValue(dao.deleteMovieFavorite(movie))
        }
    }

    fun checkfilmFavorite(id :String){
        CoroutineScope(Dispatchers.IO).launch {
            checkmovieFav.postValue(dao.checkMovieFavorite(id))
        }
    }

}