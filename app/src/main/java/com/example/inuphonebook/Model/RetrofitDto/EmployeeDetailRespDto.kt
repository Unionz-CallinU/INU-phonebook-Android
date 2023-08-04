package com.example.inuphonebook.Model.RetrofitDto


data class EmployeeDetailRespDto(
    val id : Long,
    val name : String,
    val college : String,
    val phoneNumber : String,
    val department : String,
    val role : String,
    val email : String,
    val photo : String,
)
