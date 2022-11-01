package com.solanacode.challangech6.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.solanacode.challangech6.model.MovieFav

@Database(
    entities = [MovieFav::class],
    version = 1,
    exportSchema = false
)
abstract class RoomFavorite : RoomDatabase() {
    abstract fun daoFav() : DaoFavorite
}