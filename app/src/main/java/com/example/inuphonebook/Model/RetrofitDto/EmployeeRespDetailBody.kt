package com.example.inuphonebook.Model.RetrofitDto

data class EmployeeRespDetailBody(
    val code : Int,
    val data : EmployeeDetailRespDto,
    val msg : String
)
