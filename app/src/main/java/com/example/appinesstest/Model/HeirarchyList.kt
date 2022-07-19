package com.example.appinesstest.Model

import com.google.gson.annotations.SerializedName


data class HeirarchyList (
  @SerializedName("contactName"     ) var contactName     : String? = null,
  @SerializedName("contactNumber"   ) var contactNumber   : String? = null,
  @SerializedName("designationName" ) var designationName : String? = null
)