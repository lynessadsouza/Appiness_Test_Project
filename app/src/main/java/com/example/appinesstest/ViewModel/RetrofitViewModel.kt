package com.example.appinesstest.ViewModel


import android.util.Log
import android.widget.Toast
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
    var liveDataList: MutableLiveData<List<HeirarchyList>> = MutableLiveData()
    val userSearchItem: ArrayList<HeirarchyList> = ArrayList()

    fun getLiveDataObserver(): MutableLiveData<List<HeirarchyList>> {
        return liveDataList
    }

    fun filterUsers(filteredUsers: String) {
        val user: MutableList<HeirarchyList> = mutableListOf()
        for (item in userSearchItem) {
            if (item.contactName?.lowercase()?.contains(filteredUsers.lowercase()) == true) {
                user.add(item)
            }
            else
            {
              Log.d("TAG","No MATCH FOUND")
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
                    response.body()?.dataObject?.forEach { dataObject ->
                        dataObject.myHierarchy.forEach { myHeirarchy ->
                            myHeirarchy.heirarchyList.let { userItem ->
                                for (uItem in userItem) {
                                    val heirarchyList = HeirarchyList()
                                    heirarchyList.contactName = uItem.contactName
                                    heirarchyList.contactNumber = uItem.contactNumber
                                    heirarchyList.designationName = uItem.designationName
                                    users.add(heirarchyList)
                                    userSearchItem.clear()
                                }
                            }
                        }
                    }
                    userSearchItem.addAll(users)
                    liveDataList.postValue(users)
                }
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.d(TAG, "onFailure, $call, $t")
                }
            })
        }
    }
}


