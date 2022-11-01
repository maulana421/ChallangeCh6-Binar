package com.solanacode.challangech6.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solanacode.challangech6.api.ApiUser
import com.solanacode.challangech6.model.UserResponse
import com.solanacode.challangech6.model.UserResponseUpdate
import com.solanacode.challangech6.model.UserUpdateLatest
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(val api : ApiUser): ViewModel() {

    private val getDataUser : MutableLiveData<UserResponse?> = MutableLiveData()
    private val updatedataUser : MutableLiveData<UserResponseUpdate> = MutableLiveData()

    fun getUser() : MutableLiveData<UserResponse?> = getDataUser
    fun updateUser() : MutableLiveData<UserResponseUpdate> = updatedataUser

    fun getDataUser(token : String){
        api.getUserLogin(token)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            getDataUser.postValue(body)
                        }else{
                            getDataUser.postValue(null)
                            Log.d("latest","Null")
                        }
                    }else{
                        getDataUser.postValue(null)
                        Log.d("latest",response.message())
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    getDataUser.postValue(null)
                    Log.d("latest", "gagal")
                }

            })
    }

    fun updatedataUser(token :String,name:String,email :String, password : String){
        api.updateUserLogin(token, UserUpdateLatest(name, email, password))
            .enqueue(object : Callback<UserResponseUpdate>{
                override fun onResponse(
                    call: Call<UserResponseUpdate>,
                    response: Response<UserResponseUpdate>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            updatedataUser.postValue(body)
                        }else{
                            updatedataUser.postValue(null)
                            Log.d("latest","Null")
                        }
                    }else{
                        updatedataUser.postValue(null)
                        Log.d("latest",response.message())
                    }
                }

                override fun onFailure(call: Call<UserResponseUpdate>, t: Throwable) {
                    updatedataUser.postValue(null)
                    Log.d("latest", "gagal")
                }

            })
    }


}