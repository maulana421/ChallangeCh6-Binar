package com.solanacode.challangech6.model

data class UserResponse(
    val id : Int,
    val code : String,
    val name : String,
    val email : String,
    val password : String,
    val createdAt : String,
    val updatedAt : String
)
