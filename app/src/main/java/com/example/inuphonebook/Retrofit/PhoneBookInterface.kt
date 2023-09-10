package com.example.inuphonebook.Retrofit

import com.example.inuphonebook.Model.RetrofitDto.EmployeeRespBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhoneBookInterface {

    //직원 리스트 조회
    @GET("/api/v1/employee")
    fun search(@Query("employeeSearchReqDto") search : String) : Call<EmployeeRespBody>
}