
package com.example.appinesstest.Repository

import com.example.appinesstest.Retrofit.ApiInterface
import com.example.appinesstest.Model.UserModel
import retrofit2.Call
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiInterface: ApiInterface,
) {
     fun getApiUsers(): Call<UserModel> {
        return apiInterface.getUsers()
    }

}