package com.solanacode.challangech6.model

data class UserResponseRegister(
    val status : String,
    val data : User
)

data class User(
    val user : MutableList<DataUser>
)

data class DataUser(
    val id : Int,
    val code : String,
    val name : String,
    val email : String,
    val password : String,
    val createdAt : String,
    val updatedAt : String,
    val booking : MutableList<Any>
)
