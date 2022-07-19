package com.example.appinesstest.ViewModel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appinesstest.Model.HeirarchyList
import com.example.appinesstest.Model.UserModel
import com.example.appinesstest.Repository.Repository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "RetrofitViewModel"
@HiltViewModel
class RetrofitViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var liveDataList: MutableLiveData<List<HeirarchyList>>
    val userSearchItem: MutableList<HeirarchyList> = mutableListOf()

    init {
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<HeirarchyList>> {
        return liveDataList
    }

    fun filterUsers(filteredUsrs: String) {
        val user: MutableList<HeirarchyList> = mutableListOf()
        for (item in userSearchItem) {
            if (item.contactName?.lowercase()?.contains(filteredUsrs.lowercase())!!) {
                user.add(item)
            }
        }
        liveDataList.postValue(user)
    }

    fun makeAPICall() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.remote.getApiUsers()
            response.enqueue(object : Callback<UserModel> {
                override fun onResponse(
                    call: Call<UserModel>,
                    response: Response<UserModel>
                ) {
                    val users: MutableList<HeirarchyList> = mutableListOf()
                    Log.d("Response", "${response.body()}")
                    Log.d("Response", "${response.body()?.dataObject}")
                    response.body()?.dataObject?.forEach {
                       it.myHierarchy.forEach {
                           it.heirarchyList.let { userItem->
                               for (uItem in userItem) {
                                   val heirarchyList: HeirarchyList = HeirarchyList()
                                   heirarchyList.contactName = uItem.contactName
                                   heirarchyList.contactNumber = uItem.contactNumber
                                   heirarchyList.designationName = uItem.designationName
                                   users.add(heirarchyList)
                                   userSearchItem.add(heirarchyList)
                               }
                           }
                       }
                   }
                    liveDataList.postValue(users)
                }
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.d(TAG, "onFailure, $call, $t")
                }
            })
        }
    }
}


