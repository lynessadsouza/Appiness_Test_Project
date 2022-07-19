package com.example.appinesstest.Model

import com.google.gson.annotations.SerializedName


data class DataObject (

  @SerializedName("myHierarchy" ) var myHierarchy : ArrayList<MyHierarchy> = arrayListOf()

)