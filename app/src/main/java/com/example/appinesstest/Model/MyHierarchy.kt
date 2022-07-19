package com.example.appinesstest.Model

import com.google.gson.annotations.SerializedName


data class MyHierarchy (

  @SerializedName("heirarchyList" ) var heirarchyList : ArrayList<HeirarchyList> = arrayListOf()

)