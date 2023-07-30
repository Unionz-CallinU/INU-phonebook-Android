package com.example.inuphonebook.Model

data class EmployeeResponse(
    val employee_id : Long,
    val college_name : String,
    val department_name : String,
    val name : String,
    val position : String,
    val role : String,
    val phoneNumber : String,
    val email : String
)
