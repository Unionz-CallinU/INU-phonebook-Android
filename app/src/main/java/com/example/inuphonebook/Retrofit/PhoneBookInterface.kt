package com.example.inuphonebook.Retrofit

import com.example.inuphonebook.Model.RetrofitDto.EmployeeReqDto
import com.example.inuphonebook.Model.RetrofitDto.EmployeeRespBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PhoneBookInterface {
    //직원 리스트 조회
    @POST("employee")
    fun search(@Body employeeReqDto : EmployeeReqDto) : Call<EmployeeRespBody>
}