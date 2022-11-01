package com.solanacode.challangech6.api

import android.content.Context
import androidx.room.Room
import com.solanacode.challangech6.room.DaoFavorite
import com.solanacode.challangech6.room.RoomFavorite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Api {
    const val BASE_URL = "https://ghibliapi.herokuapp.com/"
    const val BASE_URL_USER = "https://api-resto-auth.herokuapp.com/"

    @Provides
    @Singleton
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    @Named("login")
    fun setupRetrofitAuth(okHttp : OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_USER)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("movie")
    fun setupRetrofitGithub(okHttp : OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun apiEndPoint(@Named("login") retrofit : Retrofit):ApiUser = retrofit.create(ApiUser::class.java)

    @Provides
    fun authEndPoint(@Named("movie") retrofit : Retrofit):ApiService = retrofit.create(ApiService::class.java)

    @Provides
    fun roomProvides(@ApplicationContext context : Context):RoomFavorite{
        return Room.databaseBuilder(context,RoomFavorite::class.java,"DBFavorite").build()
    }

    @Singleton
    @Provides
    fun daoFavorite(db : RoomFavorite):DaoFavorite = db.daoFav()


}