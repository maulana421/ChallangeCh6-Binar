package com.solanacode.challangech6.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solanacode.challangech6.api.ApiService
import com.solanacode.challangech6.model.MovieResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MoviewViewModel @Inject constructor(var api : ApiService) : ViewModel() {

    private val liveDataFilms : MutableLiveData<List<MovieResponseItem>> = MutableLiveData()
    private val liveDataDetailFilms : MutableLiveData<MovieResponseItem> = MutableLiveData()

    fun getLiveDataFilms() : MutableLiveData<List<MovieResponseItem>> = liveDataFilms
    fun liveDataDetailFilms() : MutableLiveData<MovieResponseItem> = liveDataDetailFilms


    fun showfilmList(){
        api.getAllfilm()
            .enqueue(object : Callback<List<MovieResponseItem>>{
                override fun onResponse(
                    call: Call<List<MovieResponseItem>>,
                    response: Response<List<MovieResponseItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilms.postValue(response.body())
                        Log.d("Succes","${response.body()}")
                    }else{
                        liveDataFilms.postValue(null)
                        Log.d("notSuccess","${response.body()}")
                    }
                }

                override fun onFailure(call: Call<List<MovieResponseItem>>, t: Throwable) {
                    liveDataFilms.postValue(null)
                    Log.d("onFailure","${t.message}")
                }
            })
    }

    fun callDetailApiFilm(id: String){
        api.getDetailFilm(id)
            .enqueue(object : Callback<MovieResponseItem>{
                override fun onResponse(
                    call: Call<MovieResponseItem>,
                    response: Response<MovieResponseItem>
                ) {
                    if (response.isSuccessful){
                        liveDataDetailFilms.postValue(response.body())
                    }else{
                        liveDataDetailFilms.postValue(null)
                    }
                }

                override fun onFailure(call: Call<MovieResponseItem>, t: Throwable) {
                    liveDataDetailFilms.postValue(null)
                }

            })

    }

}