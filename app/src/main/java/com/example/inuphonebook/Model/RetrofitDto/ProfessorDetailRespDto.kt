package com.example.inuphonebook.Model.RetrofitDto

import com.google.gson.annotations.SerializedName

data class ProfessorDetailRespDto(
    @SerializedName("email")
    val email : String,
    @SerializedName("lab")
    val lab : String,
    @SerializedName("url")
    val url : String
)
