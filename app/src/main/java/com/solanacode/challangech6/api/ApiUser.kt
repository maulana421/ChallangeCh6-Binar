package com.solanacode.challangech6.api

import com.solanacode.challangech6.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiUser {
    @FormUrlEncoded
    @POST("api/v1/user/register")
    fun toRegister(@Field("name")name :String,
                   @Field("email")email :String,
                   @Field("password")password :String): Call<UserResponseRegister>

    @POST("api/v1/user/login")
    fun toLogin(@Body userLogin : UserLogin): Call<UserResponseLogin>


    @GET("api/v1/user/current")
    fun getUserLogin(@Header("Authorization")authHeader : String): Call<UserResponse>

    @PUT("api/v1/user/update")
    fun updateUserLogin(@Header("Authorization")auth: String,
                        @Body user : UserUpdateLatest
    ): Call<UserResponseUpdate>
}