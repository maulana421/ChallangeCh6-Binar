package com.solanacode.challangech6.api

import com.solanacode.challangech6.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //    film
    @GET("films/{id}")
    fun getDetailFilm(@Path("id") id : String): Call<MovieResponseItem>

    @GET("films")
    fun getAllfilm() : Call<List<MovieResponseItem>>

    //    login and register user



}