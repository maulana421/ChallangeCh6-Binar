package com.solanacode.challangech6.model

import androidx.room.PrimaryKey

data class Movie (
    val name: String,
    val image: String,
    val director: String,
    val oriTitle: String,
    val releaseDate: String
)