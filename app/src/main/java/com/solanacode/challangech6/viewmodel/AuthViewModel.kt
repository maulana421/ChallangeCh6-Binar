package com.solanacode.challangech6.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.solanacode.challangech6.api.ApiUser
import com.solanacode.challangech6.ds.UserPref
import com.solanacode.challangech6.model.UserLogin
import com.solanacode.challangech6.model.UserResponseLogin
import com.solanacode.challangech6.model.UserResponseRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    @ApplicationContext context : Context, val api : ApiUser): ViewModel() {

    private val userPreferences = UserPref(context)


    fun setToken(token : String){
        viewModelScope.launch {
            userPreferences.setToken(token)
        }
    }

    fun deleteToken(){
        viewModelScope.launch {
            userPreferences.deleteToken()
        }
    }

    fun getToken(): LiveData<String> = userPreferences.getToken().asLiveData()

    private val regist : MutableLiveData<UserResponseRegister?> = MutableLiveData()
    private val login : MutableLiveData<UserResponseLogin?> = MutableLiveData()

    fun registuser(): LiveData<UserResponseRegister?> = regist
    fun loginuser(): LiveData<UserResponseLogin?> = login

    fun userRegist(name :String,email :String,password :String){
        api.toRegister(name, email, password)
            .enqueue(object : Callback<UserResponseRegister>{
                override fun onResponse(
                    call: Call<UserResponseRegister>,
                    response: Response<UserResponseRegister>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            regist.postValue(body)
                        }else{
                            regist.postValue(null)
                            println("gagal")
                        }
                    }else{
                        regist.postValue(null)
                        println("gagal  ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponseRegister>, t: Throwable) {
                    regist.postValue(null)
                }

            })
    }

    fun toLogin(email: String, password: String){
        api.toLogin(UserLogin(email, password))
            .enqueue(object : Callback<UserResponseLogin>{
                override fun onResponse(
                    call: Call<UserResponseLogin>,
                    response: Response<UserResponseLogin>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        if(body != null){
                            login.postValue(body)
                        }else{
                            login.postValue(null)
                            println("gagal")
                        }
                    }else{
                        login.postValue(null)
                        println("gagal  ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponseLogin>, t: Throwable) {
                    login.postValue(null)
                }

            })
    }

}