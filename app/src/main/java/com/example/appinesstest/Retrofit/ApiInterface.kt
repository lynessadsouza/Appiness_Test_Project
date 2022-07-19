package com.example.appinesstest.Retrofit
import com.example.appinesstest.Model.UserModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("myHierarchy")
    fun getMovies(): Call<UserModel>
}