package com.example.inuphonebook.Model.RetrofitDto

import com.google.gson.annotations.SerializedName

data class EmployeeRespBody(
    @SerializedName("code")
    val code : Int,
    @SerializedName("data")
    val data : List<EmployeeDetailRespDto>,
    @SerializedName("msg")
    val msg : String
)
