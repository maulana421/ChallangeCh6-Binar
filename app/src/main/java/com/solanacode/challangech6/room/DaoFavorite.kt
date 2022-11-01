package com.solanacode.challangech6.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.solanacode.challangech6.model.MovieFav


@Dao
interface DaoFavorite {

    @Query("SELECT * FROM MovieFav")
    fun getMovieFavorite():MutableList<MovieFav>

    @Insert
    fun insertFavorite(movie: MovieFav)

    @Query("SELECT count(*) FROM MovieFav WHERE id= :id")
    suspend fun checkMovieFavorite(id :String):Int

    @Delete
    suspend fun deleteMovieFavorite(movie: MovieFav)
}