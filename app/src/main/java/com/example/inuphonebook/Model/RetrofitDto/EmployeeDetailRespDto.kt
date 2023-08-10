package com.example.inuphonebook.Model.RetrofitDto

import com.google.gson.annotations.SerializedName


data class EmployeeDetailRespDto(
    @SerializedName("id")
    val id : String, //Long형 test용 String
    @SerializedName("name")
    val name : String,
    @SerializedName("college")
    val college : String,
    @SerializedName("phoneNumber")
    val phoneNumber : String,
    @SerializedName("department")
    val department : String?,
    @SerializedName("role")
    val role : String?,
    @SerializedName("email")
    val email : String?,
//    @SerializedName("photo") //test용
//    val photo : String,
)
