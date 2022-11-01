package com.solanacode.challangech6.model

data class UserResponseLogin(
    val id : Int,
    val code : String,
    val name : String,
    val email : String,
    val token : String
)

data class UserLogin (
    val email : String,
    val password : String
)

