package com.example.inuphonebook.Model.RetrofitDto

data class EmployeeRespBody(
    val code : Int,
    val data : List<EmployeeRespDto>,
    val msg : String
)
