package com.example.inuphonebook.Retrofit

import com.example.inuphonebook.Model.RetrofitDto.EmployeeListRespDto
import com.example.inuphonebook.Model.RetrofitDto.EmployeeReqDto
import com.example.inuphonebook.Model.RetrofitDto.EmployeeRespBody
import com.example.inuphonebook.Model.RetrofitDto.EmployeeRespDetailBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PhoneBookInterface {
    //직원 리스트 조회
    @POST("/employee")
    fun getDescription(@Body employeeReqDto : EmployeeReqDto) : Call<EmployeeRespBody>

    //직원 상세정보 조회
    @GET("/employee/{id}")
    fun search(@Path("id") id : Long) : Call<EmployeeRespDetailBody>
}