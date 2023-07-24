package com.example.inuphonebook.Model.RetrofitDto

import com.google.gson.annotations.SerializedName

data class ProfessorDetailResult(
    @SerializedName("code")
    val code : Int,
    @SerializedName("data")
    val data : ProfessorDetailRespDto,
    @SerializedName("msg")
    val msg : String
)
