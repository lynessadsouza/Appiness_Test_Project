package com.example.appinesstest.Model

import com.google.gson.annotations.SerializedName


data class UserModel (
  @SerializedName("statusCode" ) var statusCode : Int?                  = null,
  @SerializedName("status"     ) var status     : Boolean?              = null,
  @SerializedName("dataObject" ) var dataObject : ArrayList<DataObject> = arrayListOf()

)