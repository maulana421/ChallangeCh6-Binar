package com.solanacode.challangech6.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserResponseUpdate(
    val message : String
)

@Parcelize
data class UserUpdateLatest (
    val name : String,
    val email : String,
    val password : String,
): Parcelable