package com.example.inuphonebook.Model.RetrofitDto

data class EmployeeRespBody(
    val code : Int,
    val data : EmployeeListRespDto,
    val msg : String
)
