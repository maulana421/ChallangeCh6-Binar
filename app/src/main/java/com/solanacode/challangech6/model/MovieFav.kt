package com.solanacode.challangech6.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MovieFav(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val image: String,
    val director: String,
    val oriTitle: String,
    val releaseDate: String
):Parcelable


